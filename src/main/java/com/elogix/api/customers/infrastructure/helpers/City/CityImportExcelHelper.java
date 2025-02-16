package com.elogix.api.customers.infrastructure.helpers.city;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.customers.domain.usecase.CityUseCase;
import com.elogix.api.customers.infrastructure.entry_points.dto.CityExcelResponse;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericImportExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CityImportExcelHelper extends GenericImportExcelHelper<City, CityExcelResponse> {
    private final CityUseCase useCase;

    public CityImportExcelHelper(
            ExcelHelper excelHelper,
            UpdateUtils updateUtils,
            CityUseCase useCase) {
        super(excelHelper, updateUtils);
        this.useCase = useCase;
    }

    @Override
    protected List<City> findAllEntities() {
        return useCase.findAll(getSortOrders(), false);
    }

    @Override
    protected String getEntityIdentifier(City entity) {
        return entity.getName();
    }

    @Override
    protected Set<String> getRequiredColumns() {
        return Set.of("Ciudad");
    }

    @Override
    protected boolean isIdentifierColumn(String columnName) {
        return "Ciudad".equals(columnName);
    }

    @Override
    protected City processCell(
            int cellIdx,
            Cell cell,
            City city,
            CityExcelResponse response,
            Map<Integer, String> mapColumns) {

        if (cell == null) {
            return city;
        }

        final String cellValue = excelHelper.getCleanCellValue(cell, true);
        String columnName = mapColumns.get(cellIdx);

        if (cellValue.isEmpty() || cellValue.contains("N/A")) {
            return city;
        }

        if ("Ciudad".equals(columnName) && city.getName() == null) {
            city.setName(cellValue);
        }

        return city;
    }

    @Override
    protected boolean isValidEntity(City entity, Set<String> processedItems, CityExcelResponse response) {
        if (entity.getName() == null || entity.getName().isEmpty()) {
            response.addError("Error: El nombre de la ciudad es requerido");
            return false;
        }

        if (processedItems.contains(entity.getName())) {
            response.addError("Error: La ciudad " + entity.getName() + " está duplicada");
            return false;
        }

        return true;
    }

    @Override
    protected void addToProcessedItems(City entity, Set<String> processedItems) {
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
    protected City createEntity() {
        return City.builder()
                .createdAt(Instant.now())
                .createdBy(getCurrentUser())
                .build();
    }

    @Override
    protected CityExcelResponse createResponse() {
        return new CityExcelResponse();
    }
}
