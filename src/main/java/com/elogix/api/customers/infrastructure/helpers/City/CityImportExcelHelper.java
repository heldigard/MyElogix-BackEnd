package com.elogix.api.customers.infrastructure.helpers.City;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.entry_points.dto.CityExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CityImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final CityBasicDataJpaRepository cityRepository;

    public CityExcelResponse excelToCities(InputStream inputStream) {
        CityExcelResponse response = new CityExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Set<String> cityList = new HashSet<>();
            Map<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());
            List<CityBasicData> existingList = cityRepository.findAll();
            Map<String, CityBasicData> mappedCityNames = excelHelper.mapEntities(existingList, CityBasicData::getName);

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                Integer nameIndex = excelHelper.getIndexColumn("Ciudad", mapColumns);
                if (nameIndex == -1) {
                    response.getErrors().add("Could not find 'Ciudad' column");
                    break;
                }

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(0), true);
                if (cityList.contains(cellValue)) {
                    continue;
                }

                CityData city;
                if (mappedCityNames.containsKey(cellValue)) {
                    CityBasicData cityBasicData = mappedCityNames.get(cellValue);
                    city = CityData.builder()
                            .id(cityBasicData.getId())
                            .name(cityBasicData.getName())
                            .build();
                } else {
                    city = new CityData();
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int cellIdx = currentCell.getColumnIndex();
                    city = _setupCityFromCell(
                            cellIdx,
                            currentCell,
                            city,
                            response.getErrors(),
                            mapColumns,
                            mappedCityNames);
                }

                if (!cityList.contains(city.getName())) {
                    response.getCities().add(city);
                    cityList.add(city.getName());
                }
            }

            workbook.close();

            return response;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private CityData _setupCityFromCell(
            int cellIdx,
            Cell cell,
            CityData city,
            Set<String> errors,
            Map<Integer, String> mapColumns,
            Map<String, CityBasicData> mappedCityNames) {
        final String cellValue = excelHelper.getCleanCellValue(cell, true);

        if (cellValue.isEmpty() || cellValue.contains("N/A")) {
            return city;
        }

        String columnName = mapColumns.get(cellIdx);

        if ("Ciudad".equals(columnName) && city.getName() == null) {
            city.setName(cellValue);
        }
        return city;
    }
}
