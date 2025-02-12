package com.elogix.api.customers.infrastructure.helpers.Neighborhood;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodDataJpaRepository;
import com.elogix.api.customers.infrastructure.entry_points.dto.NeighborhoodExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

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
            Map<String, String> neighborhoodList = new HashMap<>();

            Map<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());

            List<NeighborhoodData> existingList = neighborhoodRepository.findAll();
            Map<String, NeighborhoodData> mappedNeighborNames = excelHelper.mapEntities(existingList,
                    NeighborhoodData::getName);

            List<CityBasicData> existingCityList = cityRepository.findAll();
            Map<String, CityBasicData> mappedCityNames = excelHelper.mapEntities(existingCityList,
                    CityBasicData::getName);

            List<DeliveryZoneBasicData> existingDeliveryZoneList = deliveryZoneRepository.findAll();
            Map<String, DeliveryZoneBasicData> mappedZoneNames = excelHelper.mapEntities(existingDeliveryZoneList,
                    DeliveryZoneBasicData::getName);

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
                if (mappedNeighborNames.containsKey(cellValue)) {
                    neighborhood = mappedNeighborNames.get(cellValue);
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
                            mappedNeighborNames,
                            mappedCityNames,
                            mappedZoneNames);
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
            Map<Integer, String> mapColumns,
            Map<String, NeighborhoodData> mappedNeighborNames,
            Map<String, CityBasicData> mappedCityNames,
            Map<String, DeliveryZoneBasicData> mappedZoneNames) {
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
                if (neighborhood.getCity() == null && mappedCityNames.containsKey(cellValue)) {
                    neighborhood.setCity(mappedCityNames.get(cellValue));
                }

                return neighborhood;

            case "Ciudad":
                if (mappedCityNames.containsKey(cellValue)) {
                    neighborhood.setCity(mappedCityNames.get(cellValue));
                } else if (mappedNeighborNames.containsKey(cellValue)) {
                    neighborhood.setCity(mappedNeighborNames.get(cellValue).getCity());
                } else {
                    neighborhood.setCity(CityBasicData.builder().name(cellValue).build());
                }

                return neighborhood;

            case "Ruta":
                if (mappedZoneNames.containsKey(cellValue)) {
                    neighborhood.setDeliveryZone(mappedZoneNames.get(cellValue));
                } else {
                    String[] splited = cellValue.split("\\s+");
                    String ruta = splited.length > 1 ? splited[1] : splited[0];

                    try {
                        Integer rutaNumber = Integer.valueOf(ruta);

                        switch (rutaNumber) {
                            case 1:
                                neighborhood.setDeliveryZone(mappedZoneNames.get("RUTA 1"));
                                break;
                            case 2:
                                neighborhood.setDeliveryZone(mappedZoneNames.get("RUTA 2"));
                                break;
                            case 3:
                                neighborhood.setDeliveryZone(mappedZoneNames.get("RUTA 3"));
                                break;
                            case 4:
                                neighborhood.setDeliveryZone(mappedZoneNames.get("RUTA 4"));
                                break;
                            default:
                                neighborhood.setDeliveryZone(mappedZoneNames.get("EXTERNA"));
                                break;
                        }
                    } catch (Exception e) {
                        neighborhood.setDeliveryZone(mappedZoneNames.get("EXTERNA"));
                    }
                }

                return neighborhood;

            default:
                return neighborhood;
        }
    }
}
