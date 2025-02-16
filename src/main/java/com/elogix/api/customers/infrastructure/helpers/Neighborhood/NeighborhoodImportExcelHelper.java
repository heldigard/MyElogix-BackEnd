package com.elogix.api.customers.infrastructure.helpers.neighborhood;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.CityBasic;
import com.elogix.api.customers.domain.model.DeliveryZoneBasic;
import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.domain.usecase.CityBasicUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.customers.domain.usecase.NeighborhoodUseCase;
import com.elogix.api.customers.infrastructure.entry_points.dto.NeighborhoodExcelResponse;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericImportExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NeighborhoodImportExcelHelper extends GenericImportExcelHelper<Neighborhood, NeighborhoodExcelResponse> {

    private final NeighborhoodUseCase useCase;
    private final CityBasicUseCase cityUseCase;
    private final DeliveryZoneBasicUseCase zoneUseCase;
    private Map<String, CityBasic> mappedCities;
    private Map<String, DeliveryZoneBasic> mappedZones;

    public NeighborhoodImportExcelHelper(
            ExcelHelper excelHelper,
            UpdateUtils updateUtils,
            NeighborhoodUseCase useCase,
            CityBasicUseCase cityUseCase,
            DeliveryZoneBasicUseCase zoneUseCase) {
        super(excelHelper, updateUtils);
        this.useCase = useCase;
        this.cityUseCase = cityUseCase;
        this.zoneUseCase = zoneUseCase;
    }

    @Override
    protected List<Neighborhood> findAllEntities() {
        initializeMaps();
        return useCase.findAll(getSortOrders(), false);
    }

    private void initializeMaps() {
        List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));

        List<CityBasic> cities = cityUseCase.findAll(sortOrders, false);
        this.mappedCities = excelHelper.mapEntities(cities, CityBasic::getName);

        List<DeliveryZoneBasic> zones = zoneUseCase.findAll(sortOrders, false);
        this.mappedZones = excelHelper.mapEntities(zones, DeliveryZoneBasic::getName);
    }

    @Override
    protected String getEntityIdentifier(Neighborhood entity) {
        return entity.getName();
    }

    @Override
    protected Set<String> getRequiredColumns() {
        return Set.of("Sector", "Ciudad", "Ruta");
    }

    @Override
    protected boolean isIdentifierColumn(String columnName) {
        return "Sector".equals(columnName);
    }

    @Override
    protected Neighborhood processCell(
            int cellIdx,
            Cell cell,
            Neighborhood neighborhood,
            NeighborhoodExcelResponse response,
            Map<Integer, String> mapColumns) {

        if (cell == null) {
            return neighborhood;
        }

        final String cellValue = excelHelper.getCleanCellValue(cell, true);
        String columnName = mapColumns.get(cellIdx);

        if (cellValue.isEmpty() || cellValue.contains("N/A")) {
            return neighborhood;
        }

        switch (columnName) {
            case "Sector":
                neighborhood.setName(cellValue);
                break;

            case "Ciudad":
                setCity(neighborhood, cellValue);
                break;

            case "Ruta":
                setDeliveryZone(neighborhood, cellValue);
                break;
        }

        return neighborhood;
    }

    private void setCity(Neighborhood neighborhood, String cellValue) {
        if (mappedCities.containsKey(cellValue)) {
            neighborhood.setCity(mappedCities.get(cellValue));
        } else {
            neighborhood.setCity(CityBasic.builder().name(cellValue).build());
        }
    }

    private void setDeliveryZone(Neighborhood neighborhood, String cellValue) {
        if (mappedZones.containsKey(cellValue)) {
            neighborhood.setDeliveryZone(mappedZones.get(cellValue));
            return;
        }

        String[] splited = cellValue.split("\\s+");
        String ruta = splited.length > 1 ? splited[1] : splited[0];

        try {
            Integer rutaNumber = Integer.valueOf(ruta);
            String zoneName = rutaNumber >= 1 && rutaNumber <= 4
                    ? "RUTA " + rutaNumber
                    : "EXTERNA";
            neighborhood.setDeliveryZone(mappedZones.get(zoneName));
        } catch (Exception e) {
            neighborhood.setDeliveryZone(mappedZones.get("EXTERNA"));
        }
    }

    // Add these methods after the existing methods
    @Override
    protected boolean isValidEntity(Neighborhood entity, Set<String> processedItems,
            NeighborhoodExcelResponse response) {
        if (entity.getName() == null || entity.getName().isEmpty()) {
            response.addError("Error: El nombre del sector es requerido");
            return false;
        }

        if (entity.getCity() == null) {
            response.addError("Error: La ciudad es requerida para el sector %s", entity.getName());
            return false;
        }

        if (entity.getDeliveryZone() == null) {
            response.addError("Error: La ruta es requerida para el sector %s", entity.getName());
            return false;
        }

        if (processedItems.contains(entity.getName())) {
            response.addError("Error: El sector %s está duplicado", entity.getName());
            return false;
        }

        return true;
    }

    @Override
    protected void addToProcessedItems(Neighborhood entity, Set<String> processedItems) {
        if (entity != null && entity.getName() != null) {
            processedItems.add(entity.getName());
        }
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));
    }

    @Override
    protected Neighborhood createEntity() {
        return Neighborhood.builder()
                .createdAt(Instant.now())
                .createdBy(getCurrentUser())
                .build();
    }

    @Override
    protected NeighborhoodExcelResponse createResponse() {
        return new NeighborhoodExcelResponse();
    }
}
