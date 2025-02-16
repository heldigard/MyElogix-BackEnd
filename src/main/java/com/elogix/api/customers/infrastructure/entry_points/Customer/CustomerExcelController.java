package com.elogix.api.customers.infrastructure.entry_points.customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerExcelService;
import com.elogix.api.customers.infrastructure.entry_points.dto.CustomerExcelResponse;
import com.elogix.api.generics.infrastructure.entry_points.GenericExcelController;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

@RestController
@RequestMapping("/api/v1/customer/excel")
public class CustomerExcelController extends GenericExcelController<Customer, CustomerExcelResponse> {

    public CustomerExcelController(
            ExcelHelper excelHelper,
            CustomerExcelService excelService) {
        super(excelHelper, excelService);
    }

    @Override
    protected String getDownloadFilename() {
        return "customers.xlsx";
    }
}
