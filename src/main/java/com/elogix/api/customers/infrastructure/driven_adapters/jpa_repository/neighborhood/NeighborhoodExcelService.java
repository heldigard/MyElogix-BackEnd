package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.customers.infrastructure.entry_points.dto.NeighborhoodExcelResponse;
import com.elogix.api.customers.infrastructure.helpers.Neighborhood.NeighborhoodExportExcelHelper;
import com.elogix.api.customers.infrastructure.helpers.Neighborhood.NeighborhoodImportExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NeighborhoodExcelService {
    private final NeighborhoodDataJpaRepository repository;
    private final NeighborhoodImportExcelHelper importExcelHelper;
    private final NeighborhoodExportExcelHelper exportExcelHelper;

    public NeighborhoodExcelResponse save(MultipartFile file) {
        NeighborhoodExcelResponse neighborhoodsResponse = new NeighborhoodExcelResponse();
        try {
            neighborhoodsResponse = importExcelHelper.excelToNeighborhoods(file.getInputStream());
            repository.saveAll(neighborhoodsResponse.getNeighborhoods());
        } catch (IOException e) {
            neighborhoodsResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return neighborhoodsResponse;
    }

    public ByteArrayInputStream exportExcelFile() {
        List<NeighborhoodData> neighborhoods = repository.findAll();

        ByteArrayInputStream in = exportExcelHelper.neighborhoodsToExcel(neighborhoods);
        return in;
    }
}
