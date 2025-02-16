package com.elogix.api.customers.infrastructure.helpers.customer;

import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;

@Component
public class CustomerExportExcelHelper extends GenericExportExcelHelper<Customer> {

    @Override
    protected String getSheetName() {
        return "Customers";
    }

    @Override
    protected String[] getHeaders() {
        return new String[] {
                "Documento",
                "Tipo",
                "Nombre",
                "Direccion",
                "Email",
                "Telefono",
                "Contacto",
                "Celular",
                "Celular2",
                "Ciudad",
                "Sector",
                "Ruta",
                "Membresia"
        };
    }

    @Override
    protected void writeEntityToRow(Customer customer, Row row) {
        Optional<BranchOffice> office = customer.getBranchOfficeList().stream().findFirst();

        row.createCell(0).setCellValue(customer.getDocumentNumber());
        row.createCell(1).setCellValue(customer.getDocumentType().getName());
        row.createCell(2).setCellValue(customer.getName());
        row.createCell(3).setCellValue(office.map(BranchOffice::getAddress).orElse(""));
        row.createCell(4).setCellValue(customer.getEmail());
        row.createCell(5).setCellValue(customer.getPhone());

        if (office.isPresent() && office.get().getContactPerson() != null) {
            row.createCell(6).setCellValue(office.get().getContactPerson().getName());
            row.createCell(7).setCellValue(office.get().getContactPerson().getMobileNumberPrimary());
            row.createCell(8).setCellValue(office.get().getContactPerson().getMobileNumberSecondary());
        } else {
            row.createCell(6).setCellValue("");
            row.createCell(7).setCellValue("");
            row.createCell(8).setCellValue("");
        }

        if (office.isPresent()) {
            row.createCell(9).setCellValue(
                    office.get().getCity() != null ? office.get().getCity().getName() : "");
            row.createCell(10).setCellValue(
                    office.get().getNeighborhood() != null ? office.get().getNeighborhood().getName() : "");
            row.createCell(11).setCellValue(
                    office.get().getDeliveryZone() != null ? office.get().getDeliveryZone().getName() : "");
        } else {
            row.createCell(9).setCellValue("");
            row.createCell(10).setCellValue("");
            row.createCell(11).setCellValue("");
        }

        row.createCell(12).setCellValue(
                customer.getMembership() != null ? customer.getMembership().getName().toString() : "");
    }
}
