package com.elogix.api.customers.infrastructure.helpers.city;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;

@Component
public class CityExportExcelHelper extends GenericExportExcelHelper<City> {

    @Override
    protected String getSheetName() {
        return "Cities";
    }

    @Override
    protected String[] getHeaders() {
        return new String[] {
                "Ciudad"
        };
    }

    @Override
    protected void writeEntityToRow(City city, Row row) {
        row.createCell(0).setCellValue(city.getName());
    }
}
