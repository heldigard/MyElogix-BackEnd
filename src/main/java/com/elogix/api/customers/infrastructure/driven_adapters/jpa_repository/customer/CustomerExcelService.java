package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.domain.model.gateways.CustomerGateway;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.customers.infrastructure.entry_points.dto.CustomerExcelResponse;
import com.elogix.api.customers.infrastructure.helpers.customer.CustomerImportExcelHelper;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExcelService;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

@Component
public class CustomerExcelService
        extends GenericExcelService<Customer, CustomerExcelResponse, CustomerGateway, CustomerUseCase> {

    public CustomerExcelService(
            CustomerUseCase useCase,
            CustomerImportExcelHelper importExcelHelper,
            GenericExportExcelHelper<Customer> exportExcelHelper) {
        super(useCase, importExcelHelper, exportExcelHelper);
    }

    @Override
    protected CustomerExcelResponse createResponse() {
        return new CustomerExcelResponse();
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("documentNumber", "name"),
                List.of("asc", "asc"));
    }
}
