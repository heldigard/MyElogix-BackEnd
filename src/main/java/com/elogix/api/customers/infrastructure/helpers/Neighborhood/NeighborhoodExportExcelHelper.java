package com.elogix.api.customers.infrastructure.helpers.neighborhood;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;

@Component
public class NeighborhoodExportExcelHelper extends GenericExportExcelHelper<Neighborhood> {

    @Override
    protected String getSheetName() {
        return "Neighborhoods";
    }

    @Override
    protected String[] getHeaders() {
        return new String[] {
                "Sector",
                "Ciudad",
                "Ruta"
        };
    }

    @Override
    protected void writeEntityToRow(Neighborhood neighborhood, Row row) {
        row.createCell(0).setCellValue(neighborhood.getName());
        row.createCell(1).setCellValue(neighborhood.getCity().getName());
        row.createCell(2).setCellValue(neighborhood.getDeliveryZone().getName());
    }
}
