package com.elogix.api.customers.infrastructure.helpers.Neighborhood;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NeighborhoodExportExcelHelper {
    public ByteArrayInputStream neighborhoodsToExcel(List<NeighborhoodData> neighborhoods) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String[] HEADERs = {
                "Sector",
                "Ciudad",
                "Ruta"
        };

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Neighborhoods");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (NeighborhoodData neighborhood : neighborhoods) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(neighborhood.getName());
                row.createCell(1).setCellValue(neighborhood.getCity().getName());
                row.createCell(2).setCellValue(neighborhood.getDeliveryZone().getName());
            }

            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
