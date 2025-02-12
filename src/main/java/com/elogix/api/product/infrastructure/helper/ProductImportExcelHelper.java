package com.elogix.api.product.infrastructure.helper;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
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
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusDataJpaRepository;
import com.elogix.api.product.dto.ProductExcelResponse;
import com.elogix.api.product.infrastructure.repository.product.ProductData;
import com.elogix.api.product.infrastructure.repository.product.ProductDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeData;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeDataJpaRepository;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

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
            Map<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());

            List<ProductData> existingList = productRepository.findAll();
            Map<String, ProductData> mappedProductReferences = excelHelper.mapEntities(existingList,
                    ProductData::getReference);

            List<ProductTypeData> existingProductTypes = productTypeRepository.findAll();
            Map<String, ProductTypeData> mappedProductTypeNames = excelHelper.mapEntities(existingProductTypes,
                    ProductTypeData::getName);

            List<StatusData> existingStatuses = statusRepository.findAll();
            Map<String, StatusData> mappedStatusNames = excelHelper.mapEntities(existingStatuses,
                    data -> data.getName().toString());

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(0), true);
                if (referenceList.contains(cellValue)) {
                    continue;
                }

                ProductData product;
                if (mappedProductReferences.containsKey(cellValue)) {
                    product = mappedProductReferences.get(cellValue);
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
                            mappedProductTypeNames,
                            mappedStatusNames);
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
            Map<Integer, String> mapColumns,
            Map<String, ProductTypeData> mappedProductTypeNames,
            Map<String, StatusData> mappedStatusNames) {
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
                if (mappedProductTypeNames.containsKey(cellValue)) {
                    product.setType(mappedProductTypeNames.get(cellValue));
                } else {
                    errors.add("Error: " + cellValue + " SubCategoria no existe en BD, se usara OTROS" +
                            " productId: " + product.getReference());
                    product.setType(mappedProductTypeNames.get("OTROS"));
                }

                return product;

            case "Estado":
                product.setStatus(mappedStatusNames.get(cellValue));

                return product;

            default:
                return product;
        }
    }
}
