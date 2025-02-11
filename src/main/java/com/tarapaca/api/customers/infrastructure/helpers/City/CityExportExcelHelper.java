package com.tarapaca.api.customers.infrastructure.helpers.City;

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

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CityExportExcelHelper {
    public ByteArrayInputStream citiesToExcel(List<CityData> cities) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String[] HEADERs = {
                "Ciudad"
        };

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Cities");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (CityData city : cities) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(city.getName());
            }

            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
