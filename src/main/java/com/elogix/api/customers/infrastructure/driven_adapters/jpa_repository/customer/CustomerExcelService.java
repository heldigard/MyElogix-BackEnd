package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.customers.infrastructure.entry_points.dto.CustomerExcelResponse;
import com.elogix.api.customers.infrastructure.helpers.Customer.CustomerExportExcelHelper;
import com.elogix.api.customers.infrastructure.helpers.Customer.CustomerImportExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerExcelService {
    private final CustomerDataJpaRepository repository;
    private final CustomerImportExcelHelper importExcelHelper;
    private final CustomerExportExcelHelper exportExcelHelper;

    public CustomerExcelResponse importExcelFile(MultipartFile file) {
        CustomerExcelResponse customersResponse = new CustomerExcelResponse();
        try {
            customersResponse = importExcelHelper.excelToCustomers(file.getInputStream());
            repository.saveAll(customersResponse.getCustomers());
        } catch (IOException e) {
            customersResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return customersResponse;
    }

    public ByteArrayInputStream exportExcelFile() {
        List<CustomerData> customers = repository.findAll();

        ByteArrayInputStream in = exportExcelHelper.customersToExcel(customers);
        return in;
    }
}
