package com.elogix.api.shared.infraestructure.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ExcelHelper {

    private static final String EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    public boolean hasExcelFormat(MultipartFile file) {
        return file != null && EXCEL_TYPE.equals(file.getContentType());
    }

    public Map<Integer, String> mapColumns(Row row) {
        if (row == null)
            return new HashMap<>();

        return StreamSupport.stream(row.spliterator(), false)
                .filter(cell -> getValue(cell) != null)
                .collect(Collectors.toMap(
                        Cell::getColumnIndex,
                        cell -> StringUtils.capitalize(getValue(cell).toString()),
                        (existing, replacement) -> existing,
                        HashMap::new));
    }

    public String getCleanCellValue(Cell cell, boolean toUpperCase) {
        Object value = getValue(cell);
        if (value == null)
            return "";

        String cellValue = value.toString().trim().replaceAll("\\s+", " ");
        return toUpperCase ? cellValue.toUpperCase() : cellValue;
    }

    public String getCleanEmailAddress(String emailAddress) {
        return emailAddress != null ? emailAddress.replaceAll("\\s+", "").toLowerCase().trim() : "";
    }

    private Object getValue(Cell cell) {
        if (cell == null)
            return null;

        try {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case NUMERIC -> getCellValueAsString(cell);
                case BOOLEAN -> cell.getBooleanCellValue();
                case FORMULA -> getFormulaValue(cell);
                case BLANK, ERROR, _NONE -> null;
                default -> null;
            };
        } catch (Exception e) {
            log.error("Error getting cell value: {}", e.getMessage());
            return null;
        }
    }

    private Object getFormulaValue(Cell cell) {
        return switch (cell.getCachedFormulaResultType()) {
            case BOOLEAN -> cell.getBooleanCellValue();
            case NUMERIC -> getCellValueAsString(cell);
            case STRING -> cell.getStringCellValue();
            default -> cell.getCellFormula();
        };
    }

    private String getCellValueAsString(Cell cell) {
        return DATA_FORMATTER.formatCellValue(cell);
    }

    public int getNumberOfNonEmptyCells(Sheet sheet, int columnIndex) {
        if (sheet == null)
            return 0;
        return (int) StreamSupport.stream(sheet.spliterator(), false)
                .filter(Objects::nonNull)
                .map(row -> row.getCell(columnIndex))
                .filter(cell -> cell != null && cell.getCellType() != CellType.BLANK)
                .count();
    }

    // Generic method to map any entity type
    public <T, K> Map<String, T> mapEntities(List<T> entities, Function<T, String> keyExtractor) {
        if (entities == null)
            return new HashMap<>();

        return entities.stream()
                .collect(Collectors.toMap(
                        keyExtractor,
                        Function.identity(),
                        (existing, replacement) -> existing,
                        HashMap::new));
    }

    public Integer getIndexColumn(String columnName, Map<Integer, String> mapColumns) {
        return mapColumns.entrySet().stream()
                .filter(entry -> entry.getValue().equals(columnName))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }
}
