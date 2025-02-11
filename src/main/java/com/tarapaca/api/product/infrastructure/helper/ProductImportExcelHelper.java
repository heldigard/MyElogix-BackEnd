package com.tarapaca.api.product.infrastructure.helper;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusDataJpaRepository;
import com.tarapaca.api.product.dto.ProductExcelResponse;
import com.tarapaca.api.product.infrastructure.repository.product.ProductData;
import com.tarapaca.api.product.infrastructure.repository.product.ProductDataJpaRepository;
import com.tarapaca.api.product.infrastructure.repository.product_type.ProductTypeData;
import com.tarapaca.api.product.infrastructure.repository.product_type.ProductTypeDataJpaRepository;
import com.tarapaca.api.users.infrastructure.helpers.ExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final ProductDataJpaRepository productRepository;
    private final ProductTypeDataJpaRepository productTypeRepository;
    private final StatusDataJpaRepository statusRepository;

    public ProductExcelResponse excelToProducts(InputStream inputStream) {
        ProductExcelResponse response = new ProductExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Set<String> referenceList = new HashSet<>();
            HashMap<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());
            HashMap<String, ProductData> mapProducts = excelHelper.mapProducts(productRepository.findAll());
            HashMap<String, ProductTypeData> mapProductTypes = excelHelper
                    .mapProductTypes(productTypeRepository.findAll());
            HashMap<String, StatusData> mapStatuses = excelHelper.mapStatuses(statusRepository.findAll());

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(0), true);
                if (referenceList.contains(cellValue)) {
                    continue;
                }

                ProductData product;
                if (mapProducts.containsKey(cellValue)) {
                    product = mapProducts.get(cellValue);
                    product.setUpdatedAt(Instant.now());
                } else {
                    product = new ProductData();
                    product.setCreatedAt(Instant.now());
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int cellIdx = currentCell.getColumnIndex();
                    product = _setupProductFromCell(
                            cellIdx,
                            currentCell,
                            product,
                            response.getErrors(),
                            mapColumns,
                            mapProductTypes,
                            mapStatuses);
                }

                if (!referenceList.contains(product.getReference())) {
                    response.getProducts().add(product);
                    referenceList.add(product.getReference());
                }
            }

            workbook.close();

            return response;

        } catch (IOException e) {
            response.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private ProductData _setupProductFromCell(
            int cellIdx,
            Cell cell,
            ProductData product,
            Set<String> errors,
            HashMap<Integer, String> mapColumns,
            HashMap<String, ProductTypeData> mapProductTypes,
            HashMap<String, StatusData> mapStatuses) {
        final String cellValue = excelHelper.getCleanCellValue(cell, true);

        String columnName = mapColumns.get(cellIdx);

        if (cellValue.isEmpty() || cellValue.contains("N/A") || cellValue.equals("0")) {
            return product;
        }

        switch (columnName) {
            case "Referencia":
                if (product.getReference() == null) {
                    product.setReference(cellValue);
                }

                return product;

            case "Descripcion":
                product.setDescription(cellValue);

                return product;

            case "SubCategoria":
                if (mapProductTypes.containsKey(cellValue)) {
                    product.setType(mapProductTypes.get(cellValue));
                } else {
                    errors.add("Error: " + cellValue + " SubCategoria no existe en BD, se usara OTROS" +
                            " productId: " + product.getReference());
                    product.setType(mapProductTypes.get("OTROS"));
                }

                return product;

            case "Estado":
                product.setStatus(mapStatuses.get(cellValue));

                return product;

            default:
                return product;
        }
    }
}
