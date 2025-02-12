package com.elogix.api.product.infrastructure.helper;

import com.elogix.api.product.infrastructure.repository.product.ProductData;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductExportExcelHelper {
    public ByteArrayInputStream productsToExcel(List<ProductData> products) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String[] HEADERs = {
                "Referencia",
                "Descripcion",
                "Categoria",
                "SubCategoria",
                "Estado"
        };

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Products");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (ProductData product : products) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(product.getReference());
                row.createCell(1).setCellValue(product.getDescription());

                if (product.getType() != null) {
                    row.createCell(2).setCellValue(
                            product.getType().getCategory() != null ? product.getType().getCategory().getName() : ""
                    );
                    row.createCell(3).setCellValue(product.getType().getName());
                } else {
                    row.createCell(2).setCellValue("");
                    row.createCell(3).setCellValue("");
                }

                if (product.getStatus() != null) {
                    row.createCell(4).setCellValue(product.getStatus().getName().toString());
                } else {
                    row.createCell(4).setCellValue("");
                }
            }

            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
