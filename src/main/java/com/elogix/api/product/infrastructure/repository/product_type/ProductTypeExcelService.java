package com.elogix.api.product.infrastructure.repository.product_type;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericExcelService;
import com.elogix.api.product.domain.gateway.ProductTypeGateway;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.dto.ProductTypeExcelResponse;
import com.elogix.api.product.infrastructure.helper.ProductTypeExportExcelHelper;
import com.elogix.api.product.infrastructure.helper.ProductTypeImportExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

@Component
public class ProductTypeExcelService
        extends GenericExcelService<ProductType, ProductTypeExcelResponse, ProductTypeGateway, ProductTypeUseCase> {

    public ProductTypeExcelService(
            ProductTypeUseCase useCase,
            ProductTypeImportExcelHelper importExcelHelper,
            ProductTypeExportExcelHelper exportExcelHelper) {
        super(useCase, importExcelHelper, exportExcelHelper);
    }

    @Override
    protected ProductTypeExcelResponse createResponse() {
        return new ProductTypeExcelResponse();
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));
    }
}
