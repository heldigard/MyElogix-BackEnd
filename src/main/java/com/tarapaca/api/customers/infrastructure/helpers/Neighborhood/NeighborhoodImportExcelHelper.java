package com.tarapaca.api.customers.infrastructure.helpers.Neighborhood;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.entry_points.dto.NeighborhoodExcelResponse;
import com.tarapaca.api.users.infrastructure.helpers.ExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NeighborhoodImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final NeighborhoodDataJpaRepository neighborhoodRepository;
    private final CityBasicDataJpaRepository cityRepository;
    private final DeliveryZoneBasicDataJpaRepository deliveryZoneRepository;

    public NeighborhoodExcelResponse excelToNeighborhoods(InputStream inputStream) {
        NeighborhoodExcelResponse response = new NeighborhoodExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            HashMap<String, String> neighborhoodList = new HashMap<>();

            HashMap<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());
            HashMap<String, NeighborhoodData> mapNeighborhoods = excelHelper
                    .mapNeighborhoods(neighborhoodRepository.findAll());
            HashMap<String, CityBasicData> mapCities = excelHelper.mapCities(cityRepository.findAll());
            HashMap<String, DeliveryZoneBasicData> mapDeliveryZones = excelHelper
                    .mapDeliveryZones(deliveryZoneRepository.findAll());

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                Integer nameIndex = excelHelper.getIndexColumn("Sector", mapColumns);
                if (nameIndex == -1) {
                    response.getErrors().add("Could not find 'Sector' column");
                    break;
                }

                Integer cityIndex = excelHelper.getIndexColumn("Ciudad", mapColumns);
                if (cityIndex == -1) {
                    response.getErrors().add("Could not find 'Ciudad' column");
                    break;
                }

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(nameIndex), true);
                if (neighborhoodList.containsKey(cellValue)) {
                    String city = excelHelper.getCleanCellValue(currentRow.getCell(cityIndex), true);
                    if (city.equals(neighborhoodList.get(cellValue)))
                        response.getErrors().add("Error: Registro duplicado: "
                                + cellValue + " en " + city);
                    continue;
                }

                NeighborhoodData neighborhood;
                if (mapNeighborhoods.containsKey(cellValue)) {
                    neighborhood = mapNeighborhoods.get(cellValue);
                } else {
                    neighborhood = new NeighborhoodData();
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int cellIdx = currentCell.getColumnIndex();
                    neighborhood = _setupNeighborhoodFromCell(
                            cellIdx,
                            currentCell,
                            neighborhood,
                            response.getErrors(),
                            mapColumns,
                            mapNeighborhoods,
                            mapCities,
                            mapDeliveryZones);
                }

                if (!(neighborhoodList.containsKey(neighborhood.getName())
                        && neighborhoodList.get(neighborhood.getName()).equals(neighborhood.getCity().getName()))) {
                    response.getNeighborhoods().add(neighborhood);
                    neighborhoodList.put(neighborhood.getName(), neighborhood.getCity().getName());
                } else {
                    response.getErrors().add("Error: Registro duplicado: "
                            + neighborhood.getName() + " en " + neighborhood.getCity().getName());
                }
            }

            workbook.close();

            return response;

        } catch (IOException e) {
            response.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private NeighborhoodData _setupNeighborhoodFromCell(
            int cellIdx,
            Cell cell,
            NeighborhoodData neighborhood,
            Set<String> errors,
            HashMap<Integer, String> mapColumns,
            HashMap<String, NeighborhoodData> mapNeighborhoods,
            HashMap<String, CityBasicData> mapCities,
            HashMap<String, DeliveryZoneBasicData> mapDeliveryZones) {
        final String cellValue = excelHelper.getCleanCellValue(cell, true);

        if (cellValue.isEmpty() || cellValue.contains("N/A")) {
            return neighborhood;
        }

        String columnName = mapColumns.get(cellIdx);

        switch (columnName) {
            case "Sector":
                if (neighborhood.getName() == null) {
                    neighborhood.setName(cellValue);
                }
                if (neighborhood.getCity() == null) {
                    if (mapCities.containsKey(cellValue)) {
                        neighborhood.setCity(mapCities.get(cellValue));
                    }
                }

                return neighborhood;

            case "Ciudad":
                if (mapCities.containsKey(cellValue)) {
                    neighborhood.setCity(mapCities.get(cellValue));
                } else if (mapNeighborhoods.containsKey(cellValue)) {
                    neighborhood.setCity(mapNeighborhoods.get(cellValue).getCity());
                } else {
                    neighborhood.setCity(CityBasicData.builder().name(cellValue).build());
                }

                return neighborhood;

            case "Ruta":
                if (mapDeliveryZones.containsKey(cellValue)) {
                    neighborhood.setDeliveryZone(mapDeliveryZones.get(cellValue));
                } else {
                    String[] splited = cellValue.split("\\s+");
                    String ruta = splited.length > 1 ? splited[1] : splited[0];

                    try {
                        Integer rutaNumber = Integer.valueOf(ruta);

                        switch (rutaNumber) {
                            case 1:
                                neighborhood.setDeliveryZone(mapDeliveryZones.get("RUTA 1"));
                                break;
                            case 2:
                                neighborhood.setDeliveryZone(mapDeliveryZones.get("RUTA 2"));
                                break;
                            case 3:
                                neighborhood.setDeliveryZone(mapDeliveryZones.get("RUTA 3"));
                                break;
                            case 4:
                                neighborhood.setDeliveryZone(mapDeliveryZones.get("RUTA 4"));
                                break;
                            default:
                                neighborhood.setDeliveryZone(mapDeliveryZones.get("EXTERNA"));
                                break;
                        }
                    } catch (Exception e) {
                        neighborhood.setDeliveryZone(mapDeliveryZones.get("EXTERNA"));
                    }
                }

                return neighborhood;

            default:
                return neighborhood;
        }
    }
}
