package com.elogix.api.generics.infrastructure.helpers.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;

import com.elogix.api.generics.domain.model.GenericEntity;
import com.elogix.api.generics.infrastructure.dto.ExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.UserBasic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GenericImportExcelHelper<T extends GenericEntity, R extends ExcelResponse<T>> {
    protected final ExcelHelper excelHelper;
    protected final UpdateUtils updateUtils;

    protected GenericImportExcelHelper(ExcelHelper excelHelper, UpdateUtils updateUtils) {
        this.excelHelper = excelHelper;
        this.updateUtils = updateUtils;
    }

    protected abstract List<T> findAllEntities();

    protected abstract String getEntityIdentifier(T entity);

    public R processExcel(InputStream inputStream) throws IOException {
        R response = createResponse();
        Set<String> processedItems = new HashSet<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Map<Integer, String> columnMap = excelHelper.getColumnMap(rows);
            excelHelper.validateRequiredColumns(columnMap, response.getErrors(), getRequiredColumns());

            if (!response.getErrors().isEmpty()) {
                return response;
            }

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);
            processRows(rows, numOfNonEmptyCells - 1, columnMap, processedItems, response);
        }

        return response;
    }

    protected void processRows(
            Iterator<Row> rows,
            int numRows,
            Map<Integer, String> columnMap,
            Set<String> processedItems,
            R response) {

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            if (!rows.hasNext())
                break;

            Row currentRow = rows.next();
            String identifier = null;

            // First find the identifier
            for (Map.Entry<Integer, String> entry : columnMap.entrySet()) {
                if (isIdentifierColumn(entry.getValue())) {
                    Cell cell = currentRow.getCell(entry.getKey());
                    if (cell != null) {
                        identifier = excelHelper.getCleanCellValue(cell, true);
                    }
                    break;
                }
            }

            List<T> entities = findAllEntities();
            Map<String, T> existingEntities = excelHelper.mapEntities(entities, this::getEntityIdentifier);

            // Get or create entity based on identifier
            T entity = (identifier != null && existingEntities.containsKey(identifier))
                    ? existingEntities.get(identifier)
                    : createEntity();

            // Process all cells
            for (int cellIdx = 0; cellIdx < columnMap.size(); cellIdx++) {
                entity = processCell(cellIdx, currentRow.getCell(cellIdx), entity, response, columnMap);
            }

            if (isValidEntity(entity, processedItems, response)) {
                response.getEntities().add(entity);
                addToProcessedItems(entity, processedItems);
            }
        }
    }

    protected abstract boolean isIdentifierColumn(String columnName);

    protected abstract boolean isValidEntity(T entity, Set<String> processedItems, R response);

    protected abstract void addToProcessedItems(T entity, Set<String> processedItems);

    protected abstract R createResponse();

    protected abstract T createEntity();

    protected abstract T processCell(int cellIdx, Cell cell, T entity,
            R response, Map<Integer, String> columnMap);

    protected abstract List<Sort.Order> getSortOrders();

    protected UserBasic getCurrentUser() {
        return updateUtils.getCurrentUser();
    }

    protected abstract Set<String> getRequiredColumns();
}
