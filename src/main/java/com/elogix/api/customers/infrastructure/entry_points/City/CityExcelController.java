package com.elogix.api.customers.infrastructure.entry_points.city;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityExcelService;
import com.elogix.api.customers.infrastructure.entry_points.dto.CityExcelResponse;
import com.elogix.api.generics.infrastructure.entry_points.GenericExcelController;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

@RestController
@RequestMapping("/api/v1/city/excel")
public class CityExcelController extends GenericExcelController<City, CityExcelResponse> {

    public CityExcelController(
            ExcelHelper excelHelper,
            CityExcelService excelService) {
        super(excelHelper, excelService);
    }

    @Override
    protected String getDownloadFilename() {
        return "cities.xlsx";
    }
}
