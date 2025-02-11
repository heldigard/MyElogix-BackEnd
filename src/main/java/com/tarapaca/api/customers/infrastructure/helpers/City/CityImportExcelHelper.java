package com.tarapaca.api.customers.infrastructure.helpers.City;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.entry_points.dto.CityExcelResponse;
import com.tarapaca.api.users.infrastructure.helpers.ExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CityImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final CityBasicDataJpaRepository cityBasicRepository;

    public CityExcelResponse excelToCities(InputStream inputStream) {
        CityExcelResponse response = new CityExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Set<String> cityList = new HashSet<>();

            HashMap<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());
            HashMap<String, CityBasicData> mapCities = excelHelper.mapCities(cityBasicRepository.findAll());

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
                if (mapCities.containsKey(cellValue)) {
                    CityBasicData cityBasicData = mapCities.get(cellValue);
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
                            mapCities);
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
            HashMap<Integer, String> mapColumns,
            HashMap<String, CityBasicData> mapCities) {
        final String cellValue = excelHelper.getCleanCellValue(cell, true);

        if (cellValue.isEmpty() || cellValue.contains("N/A")) {
            return city;
        }

        String columnName = mapColumns.get(cellIdx);

        switch (columnName) {
            case "Ciudad":
                if (city.getName() == null) {
                    city.setName(cellValue);
                }

                return city;

            default:
                return city;
        }
    }
}
