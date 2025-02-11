package com.tarapaca.api.customers.infrastructure.helpers.Customer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerExportExcelHelper {
    public ByteArrayInputStream customersToExcel(List<CustomerData> customers) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String[] HEADERs = {
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

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Customers");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (CustomerData customer : customers) {
                Row row = sheet.createRow(rowIdx++);
                Optional<BranchOfficeData> branchOffice = customer.getBranchOfficeList().stream().findFirst();

                row.createCell(0).setCellValue(customer.getDocumentNumber());
                row.createCell(1).setCellValue(customer.getDocumentType().getName());
                row.createCell(2).setCellValue(customer.getName());

                if (branchOffice.isPresent()) {
                    row.createCell(3).setCellValue(branchOffice.get().getAddress());
                } else {
                    row.createCell(3).setCellValue("");
                }

                row.createCell(4).setCellValue(customer.getEmail());
                row.createCell(5).setCellValue(customer.getPhone());

                if (branchOffice.isPresent() && branchOffice.get().getContactPerson() != null) {
                    row.createCell(6).setCellValue(branchOffice.get().getContactPerson().getName());
                    row.createCell(7).setCellValue(branchOffice.get().getContactPerson().getMobileNumberPrimary());
                    row.createCell(8).setCellValue(branchOffice.get().getContactPerson().getMobileNumberSecondary());
                } else {
                    row.createCell(6).setCellValue("");
                    row.createCell(7).setCellValue("");
                    row.createCell(8).setCellValue("");
                }

                if (branchOffice.isPresent() && branchOffice.get().getCity() != null) {
                    row.createCell(9).setCellValue(branchOffice.get().getCity().getName());
                } else {
                    row.createCell(9).setCellValue("");
                }

                if (branchOffice.isPresent() && branchOffice.get().getNeighborhood() != null) {
                    row.createCell(10).setCellValue(branchOffice.get().getNeighborhood().getName());
                } else {
                    row.createCell(10).setCellValue("");
                }

                if (branchOffice.isPresent() && branchOffice.get().getDeliveryZone() != null) {
                    row.createCell(11).setCellValue(branchOffice.get().getDeliveryZone().getName());
                } else {
                    row.createCell(11).setCellValue("");
                }

                if (customer.getMembership() != null) {
                    row.createCell(12).setCellValue(customer.getMembership().getName().toString());
                } else {
                    row.createCell(12).setCellValue("");
                }
            }

            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
