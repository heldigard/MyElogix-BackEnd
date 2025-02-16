package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.elogix.api.customers.domain.usecase.NeighborhoodUseCase;
import com.elogix.api.customers.infrastructure.entry_points.dto.NeighborhoodExcelResponse;
import com.elogix.api.customers.infrastructure.helpers.neighborhood.NeighborhoodExportExcelHelper;
import com.elogix.api.customers.infrastructure.helpers.neighborhood.NeighborhoodImportExcelHelper;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExcelService;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

@Component
public class NeighborhoodExcelService
        extends GenericExcelService<Neighborhood, NeighborhoodExcelResponse, NeighborhoodGateway, NeighborhoodUseCase> {

    public NeighborhoodExcelService(
            NeighborhoodUseCase useCase,
            NeighborhoodImportExcelHelper importExcelHelper,
            NeighborhoodExportExcelHelper exportExcelHelper) {
        super(useCase, importExcelHelper, exportExcelHelper);
    }

    @Override
    protected NeighborhoodExcelResponse createResponse() {
        return new NeighborhoodExcelResponse();
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));
    }
}
