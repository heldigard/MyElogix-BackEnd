package com.tarapaca.api.product.infrastructure.helper;

import com.tarapaca.api.product.dto.ProductTypeExcelResponse;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.tarapaca.api.product.infrastructure.repository.product_type.ProductTypeData;
import com.tarapaca.api.product.infrastructure.repository.product_type.ProductTypeDataJpaRepository;
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
public class ProductTypeImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final ProductTypeDataJpaRepository typeRepository;
    private final ProductCategoryDataJpaRepository categoryRepository;

    public ProductTypeExcelResponse excelToProductTypes(InputStream inputStream) {
        ProductTypeExcelResponse response = new ProductTypeExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Set<String> nameList = new HashSet<>();
            HashMap<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());
            HashMap<String, ProductTypeData> mapTypes = excelHelper.mapProductTypes(typeRepository.findAll());
            HashMap<String, ProductCategoryData> mapCategories = excelHelper.mapProductCategories(categoryRepository.findAll());

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                Integer nameIndex = excelHelper.getIndexColumn("SubCategoria", mapColumns);
                if (nameIndex == -1) {
                    response.getErrors().add("Could not find 'SubCategoria' column");
                    break;
                }

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(nameIndex), true);
                if (nameList.contains(cellValue)) {
                    continue;
                }

                ProductTypeData productType;
                if (mapTypes.containsKey(cellValue)) {
                    productType = mapTypes.get(cellValue);
                } else {
                    productType = new ProductTypeData();
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int cellIdx = currentCell.getColumnIndex();
                    productType = _setupProductTypeFromCell(
                            cellIdx,
                            currentCell,
                            productType,
                            response.getErrors(),
                            mapColumns,
                            mapTypes,
                            mapCategories
                    );
                }

                if (!nameList.contains(productType.getName())) {
                    response.getProductTypes().add(productType);
                    nameList.add(productType.getName());
                }
            }

            workbook.close();

            return response;

        } catch (IOException e) {
            response.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private ProductTypeData _setupProductTypeFromCell(
            int cellIdx,
            Cell cell,
            ProductTypeData productType,
            Set<String> errors,
            HashMap<Integer, String> mapColumns,
            HashMap<String, ProductTypeData> mapTypes,
            HashMap<String, ProductCategoryData> mapCategories
    ) {
        final String cellValue = excelHelper.getCleanCellValue(cell, true);

        String columnName = mapColumns.get(cellIdx);

        if (cellValue.isEmpty() || cellValue.contains("N/A") || cellValue.equals("0")) {
            return productType;
        }

        switch (columnName) {
            case "SubCategoria":
                if (productType.getName() == null) {
                    productType.setName(cellValue);
                }

                return productType;

            case "Categoria":
                if (
                        productType.getCategory() != null
                                && Objects.equals(productType.getCategory().getName(), cellValue)
                ) {
                    return productType;
                }

                ProductCategoryData productCategory;
                if (mapCategories.containsKey(cellValue)) {
                    productCategory = mapCategories.get(cellValue);
                } else {
                    ProductCategoryData.ProductCategoryDataBuilder pb = ProductCategoryData.builder();
                    productCategory = pb.name(cellValue).build();
                    mapCategories.put(cellValue, productCategory);
                }

                productType.setCategory(productCategory);

                return productType;

            default:
                return productType;
        }
    }
}
