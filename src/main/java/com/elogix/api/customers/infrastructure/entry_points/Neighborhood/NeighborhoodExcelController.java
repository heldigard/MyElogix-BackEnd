package com.elogix.api.customers.infrastructure.entry_points.neighborhood;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodExcelService;
import com.elogix.api.customers.infrastructure.entry_points.dto.NeighborhoodExcelResponse;
import com.elogix.api.generics.infrastructure.entry_points.GenericExcelController;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

@RestController
@RequestMapping("/api/v1/neighborhood/excel")
public class NeighborhoodExcelController extends GenericExcelController<Neighborhood, NeighborhoodExcelResponse> {

    public NeighborhoodExcelController(
            ExcelHelper excelHelper,
            NeighborhoodExcelService excelService) {
        super(excelHelper, excelService);
    }

    @Override
    protected String getDownloadFilename() {
        return "neighborhoods.xlsx";
    }
}
