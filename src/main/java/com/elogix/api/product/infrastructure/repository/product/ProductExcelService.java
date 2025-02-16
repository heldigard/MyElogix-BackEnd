package com.elogix.api.product.infrastructure.repository.product;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericExcelService;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericImportExcelHelper;
import com.elogix.api.product.domain.gateway.ProductGateway;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product.dto.ProductExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

@Component
public class ProductExcelService
        extends GenericExcelService<Product, ProductExcelResponse, ProductGateway, ProductUseCase> {

    public ProductExcelService(
            ProductUseCase useCase,
            GenericImportExcelHelper<Product, ProductExcelResponse> importExcelHelper,
            GenericExportExcelHelper<Product> exportExcelHelper) {
        super(useCase, importExcelHelper, exportExcelHelper);
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
}
