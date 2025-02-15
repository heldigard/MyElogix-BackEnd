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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product.dto.ProductExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;
import com.elogix.api.users.domain.model.UserBasic;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final ProductUseCase useCase;
    private final ProductTypeUseCase productTypeUseCase;
    private final StatusUseCase statusUseCase;
    private final UpdateUtils updateUtils;

    public ProductExcelResponse excelToProducts(InputStream inputStream) {
        ProductExcelResponse response = new ProductExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Set<String> referenceList = new HashSet<>();
            Map<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());

            List<String> properties = List.of("reference");
            List<String> directions = List.of("asc");
            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            List<Product> existingList = useCase.findAll(sortOrders, false);
            Map<String, Product> mappedProductReferences = excelHelper.mapEntities(existingList,
                    Product::getReference);

            properties = List.of("name");
            sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            List<ProductType> existingProductTypes = productTypeUseCase.findAll(sortOrders, false);
            Map<String, ProductType> mappedProductTypeNames = excelHelper.mapEntities(existingProductTypes,
                    ProductType::getName);

            properties = List.of("name");
            sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            List<Status> existingStatuses = statusUseCase.findAll(sortOrders, false);
            Map<String, Status> mappedStatusNames = excelHelper.mapEntities(existingStatuses,
                    data -> data.getName().toString());

            UserBasic currentUser = updateUtils.getCurrentUser();
            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(0), true);
                if (referenceList.contains(cellValue)) {
                    continue;
                }

                Product product;
                if (mappedProductReferences.containsKey(cellValue)) {
                    product = mappedProductReferences.get(cellValue);
                    product.setUpdatedAt(Instant.now());
                    product.setUpdatedBy(currentUser);
                } else {
                    product = Product.builder()
                            .createdAt(Instant.now())
                            .createdBy(currentUser)
                            .isActive(true)
                            .hits(0L)
                            .build();
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

    private Product _setupProductFromCell(
            int cellIdx,
            Cell cell,
            Product product,
            Set<String> errors,
            Map<Integer, String> mapColumns,
            Map<String, ProductType> mappedProductTypeNames,
            Map<String, Status> mappedStatusNames) {
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
                if (product.getStatus().getName() == EStatus.LOW_STOCK) {
                    product.setLowStock(true);
                }
                if (product.getStatus().getName() == EStatus.OUT_OF_STOCK) {
                    product.setActive(false);
                }

                return product;

            default:
                return product;
        }
    }
}
