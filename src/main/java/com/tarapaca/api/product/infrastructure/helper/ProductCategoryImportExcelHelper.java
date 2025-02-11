package com.tarapaca.api.product.infrastructure.helper;

import com.tarapaca.api.product.dto.ProductCategoryExcelResponse;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.tarapaca.api.users.infrastructure.helpers.ExcelHelper;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
@AllArgsConstructor
public class ProductCategoryImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final ProductCategoryDataJpaRepository repository;

    public ProductCategoryExcelResponse excelToProductCategories(InputStream inputStream) {
        ProductCategoryExcelResponse response = new ProductCategoryExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Set<String> nameList = new HashSet<>();
            HashMap<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());
            HashMap<String, ProductCategoryData> mapCategories = excelHelper.mapProductCategories(repository.findAll());

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                Integer nameIndex = excelHelper.getIndexColumn("Categoria", mapColumns);
                if (nameIndex == -1) {
                    response.getErrors().add("Could not find 'Categoria' column");
                    break;
                }

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(nameIndex), true);
                if (nameList.contains(cellValue)) {
                    continue;
                }

                ProductCategoryData productCategory;
                if (mapCategories.containsKey(cellValue)) {
                    productCategory = mapCategories.get(cellValue);
                } else {
                    productCategory = new ProductCategoryData();
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int cellIdx = currentCell.getColumnIndex();
                    productCategory = _setupProductCategoryFromCell(
                            cellIdx,
                            currentCell,
                            productCategory,
                            response.getErrors(),
                            mapColumns,
                            mapCategories
                    );
                }

                if (!nameList.contains(productCategory.getName())) {
                    response.getProductCategories().add(productCategory);
                    nameList.add(productCategory.getName());
                }
            }

            workbook.close();

            return response;

        } catch (IOException e) {
            response.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private ProductCategoryData _setupProductCategoryFromCell(
            int cellIdx,
            Cell cell,
            ProductCategoryData productCategory,
            Set<String> errors,
            HashMap<Integer, String> mapColumns,
            HashMap<String, ProductCategoryData> mapCategories
    ) {
        final String cellValue = excelHelper.getCleanCellValue(cell, true);

        String columnName = mapColumns.get(cellIdx);

        if (cellValue.isEmpty() || cellValue.contains("N/A") || cellValue.equals("0")) {
            return productCategory;
        }

        switch (columnName) {
            case "Categoria":
                if (productCategory.getName() == null) {
                    productCategory.setName(cellValue);
                }

                return productCategory;

            default:
                return productCategory;
        }
    }
}
