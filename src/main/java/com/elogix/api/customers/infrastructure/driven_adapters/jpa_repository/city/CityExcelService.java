package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.customers.domain.model.gateways.CityGateway;
import com.elogix.api.customers.domain.usecase.CityUseCase;
import com.elogix.api.customers.infrastructure.entry_points.dto.CityExcelResponse;
import com.elogix.api.customers.infrastructure.helpers.city.CityExportExcelHelper;
import com.elogix.api.customers.infrastructure.helpers.city.CityImportExcelHelper;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExcelService;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

@Component
public class CityExcelService extends GenericExcelService<City, CityExcelResponse, CityGateway, CityUseCase> {

    public CityExcelService(
            CityUseCase useCase,
            CityImportExcelHelper importExcelHelper,
            CityExportExcelHelper exportExcelHelper) {
        super(useCase, importExcelHelper, exportExcelHelper);
    }

    @Override
    protected CityExcelResponse createResponse() {
        return new CityExcelResponse();
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));
    }
}
