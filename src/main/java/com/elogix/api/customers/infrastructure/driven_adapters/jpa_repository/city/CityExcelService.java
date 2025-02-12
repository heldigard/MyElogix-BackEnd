package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.customers.infrastructure.entry_points.dto.CityExcelResponse;
import com.elogix.api.customers.infrastructure.helpers.City.CityExportExcelHelper;
import com.elogix.api.customers.infrastructure.helpers.City.CityImportExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CityExcelService {
    private final CityDataJpaRepository repository;
    private final CityImportExcelHelper importExcelHelper;
    private final CityExportExcelHelper exportExcelHelper;

    public CityExcelResponse importExcelFile(MultipartFile file) {
        CityExcelResponse citiesResponse = new CityExcelResponse();
        try {
            citiesResponse = importExcelHelper.excelToCities(file.getInputStream());
            repository.saveAll(citiesResponse.getCities());
        } catch (IOException e) {
            citiesResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return citiesResponse;
    }

    public ByteArrayInputStream exportExcelFile() {
        List<CityData> cities = repository.findAll();

        ByteArrayInputStream in = exportExcelHelper.citiesToExcel(cities);
        return in;
    }
}
