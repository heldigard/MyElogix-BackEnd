package com.elogix.api.product.infrastructure.helper;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;
import com.elogix.api.product.domain.model.Product;

@Component
public class ProductExportExcelHelper extends GenericExportExcelHelper<Product> {

    @Override
    protected String getSheetName() {
        return "Products";
    }

    @Override
    protected String[] getHeaders() {
        return new String[] {
                "Referencia",
                "Descripcion",
                "Categoria",
                "SubCategoria",
                "Estado"
        };
    }

    @Override
    protected void writeEntityToRow(Product product, Row row) {
        row.createCell(0).setCellValue(product.getReference());
        row.createCell(1).setCellValue(product.getDescription());

        if (product.getType() != null) {
            row.createCell(2).setCellValue(
                    product.getType().getCategory() != null ? product.getType().getCategory().getName() : "");
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
}
