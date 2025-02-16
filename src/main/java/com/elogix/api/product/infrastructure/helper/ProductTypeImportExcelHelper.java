package com.elogix.api.product.infrastructure.helper;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericImportExcelHelper;
import com.elogix.api.product.domain.model.ProductCategory;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.domain.usecase.ProductCategoryUseCase;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.dto.ProductTypeExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductTypeImportExcelHelper extends GenericImportExcelHelper<ProductType, ProductTypeExcelResponse> {

    private final ProductTypeUseCase useCase;
    private final ProductCategoryUseCase categoryUseCase;
    private Map<String, ProductCategory> mappedCategories;

    public ProductTypeImportExcelHelper(
            ExcelHelper excelHelper,
            UpdateUtils updateUtils,
            ProductTypeUseCase useCase,
            ProductCategoryUseCase categoryUseCase) {
        super(excelHelper, updateUtils);
        this.useCase = useCase;
        this.categoryUseCase = categoryUseCase;
    }

    @Override
    protected List<ProductType> findAllEntities() {
        initializeMaps();
        return useCase.findAll(getSortOrders(), false);
    }

    private void initializeMaps() {
        List<ProductCategory> categories = categoryUseCase.findAll(getSortOrders(), false);
        this.mappedCategories = excelHelper.mapEntities(categories, ProductCategory::getName);
    }

    @Override
    protected String getEntityIdentifier(ProductType entity) {
        return entity.getName();
    }

    @Override
    protected Set<String> getRequiredColumns() {
        return Set.of("SubCategoria", "Categoria");
    }

    @Override
    protected boolean isIdentifierColumn(String columnName) {
        return "SubCategoria".equals(columnName);
    }

    @Override
    protected ProductType processCell(
            int cellIdx,
            Cell cell,
            ProductType productType,
            ProductTypeExcelResponse response,
            Map<Integer, String> mapColumns) {

        if (cell == null) {
            return productType;
        }

        final String cellValue = excelHelper.getCleanCellValue(cell, true);
        String columnName = mapColumns.get(cellIdx);

        if (cellValue.isEmpty() || cellValue.contains("N/A")) {
            return productType;
        }

        switch (columnName) {
            case "SubCategoria":
                productType.setName(cellValue);
                break;

            case "Categoria":
                setCategory(productType, cellValue);
                break;
        }

        return productType;
    }

    private void setCategory(ProductType productType, String cellValue) {
        if (mappedCategories.containsKey(cellValue)) {
            productType.setCategory(mappedCategories.get(cellValue));
        } else {
            productType.setCategory(ProductCategory.builder()
                    .name(cellValue)
                    .build());
        }
    }

    @Override
    protected boolean isValidEntity(ProductType entity, Set<String> processedItems, ProductTypeExcelResponse response) {
        if (entity.getName() == null || entity.getName().isEmpty()) {
            response.addError("Error: El nombre de la subcategoría es requerido");
            return false;
        }

        if (entity.getCategory() == null) {
            response.addError("Error: La categoría es requerida para la subcategoría %s", entity.getName());
            return false;
        }

        if (processedItems.contains(entity.getName())) {
            response.addError("Error: La subcategoría %s está duplicada", entity.getName());
            return false;
        }

        return true;
    }

    @Override
    protected void addToProcessedItems(ProductType entity, Set<String> processedItems) {
        if (entity != null && entity.getName() != null) {
            processedItems.add(entity.getName());
        }
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));
    }

    @Override
    protected ProductType createEntity() {
        return ProductType.builder()
                .createdAt(Instant.now())
                .createdBy(getCurrentUser())
                .build();
    }

    @Override
    protected ProductTypeExcelResponse createResponse() {
        return new ProductTypeExcelResponse();
    }
}
