package com.elogix.api.product.infrastructure.helper;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericImportExcelHelper;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product.dto.ProductExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

@Component
public class ProductImportExcelHelper extends GenericImportExcelHelper<Product, ProductExcelResponse> {
    private final ProductUseCase useCase;
    private final ProductTypeUseCase productTypeUseCase;
    private final StatusUseCase statusUseCase;

    private Map<String, ProductType> typeMap;
    private Map<String, Status> statusMap;

    public ProductImportExcelHelper(
            ExcelHelper excelHelper,
            UpdateUtils updateUtils,
            ProductUseCase useCase,
            ProductTypeUseCase productTypeUseCase,
            StatusUseCase statusUseCase) {
        super(excelHelper, updateUtils);
        this.useCase = useCase;
        this.productTypeUseCase = productTypeUseCase;
        this.statusUseCase = statusUseCase;
    }

    private void initializeMaps() {
        List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));

        // Cargar tipos de producto
        List<ProductType> types = productTypeUseCase.findAll(sortOrders, false);
        this.typeMap = excelHelper.mapEntities(types, ProductType::getName);

        // Cargar estados
        List<Status> statuses = statusUseCase.findAll(sortOrders, false);
        this.statusMap = excelHelper.mapEntities(statuses,
                status -> status.getName().toString());
    }

    @Override
    protected List<Product> findAllEntities() {
        initializeMaps();
        return useCase.findAll(getSortOrders(), false);
    }

    @Override
    protected String getEntityIdentifier(Product entity) {
        return entity.getReference();
    }

    @Override
    protected Product createEntity() {
        return Product.builder()
                .createdAt(Instant.now())
                .createdBy(updateUtils.getCurrentUser())
                .isActive(true)
                .hits(0L)
                .build();
    }

    @Override
    protected Product processCell(
            int cellIdx,
            Cell cell,
            Product product,
            ProductExcelResponse response,
            Map<Integer, String> mapColumns) {
        if (cell == null) {
            return product;
        }

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
                break;

            case "Descripcion":
                product.setDescription(cellValue);
                break;

            case "SubCategoria":
                setProductType(product, cellValue, response);
                break;

            case "Estado":
                setProductStatus(product, cellValue);
                break;
        }

        return product;
    }

    @Override
    protected Set<String> getRequiredColumns() {
        return Set.of("Referencia", "Descripcion", "SubCategoria", "Estado");
    }

    @Override
    protected ProductExcelResponse createResponse() {
        return new ProductExcelResponse();
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("reference"),
                List.of("asc"));
    }

    @Override
    protected boolean isIdentifierColumn(String columnName) {
        return "Referencia".equals(columnName);
    }

    @Override
    protected boolean isValidEntity(Product entity, Set<String> processedItems, ProductExcelResponse response) {
        if (entity == null || entity.getReference() == null || entity.getReference().isEmpty()) {
            response.addError("Error: La referencia es requerida");
            return false;
        }

        if (processedItems.contains(entity.getReference())) {
            response.addError("Error: La referencia " + entity.getReference() + " está duplicada");
            return false;
        }

        return true;
    }

    @Override
    protected void addToProcessedItems(Product entity, Set<String> processedItems) {
        if (entity != null && entity.getReference() != null) {
            processedItems.add(entity.getReference());
        }
    }

    private void setProductType(Product product, String typeName, ProductExcelResponse response) {
        if (typeMap.containsKey(typeName)) {
            product.setType(typeMap.get(typeName));
        } else {
            response.addError("Error: " + typeName + " SubCategoria no existe en BD, se usara OTROS" +
                    " productId: " + product.getReference());
            product.setType(typeMap.get("OTROS"));
        }
    }

    private void setProductStatus(Product product, String statusName) {
        Status status = statusMap.get(statusName);
        product.setStatus(status);

        if (status.getName() == EStatus.LOW_STOCK) {
            product.setLowStock(true);
        }
        if (status.getName() == EStatus.OUT_OF_STOCK) {
            product.setActive(false);
        }
    }
}
