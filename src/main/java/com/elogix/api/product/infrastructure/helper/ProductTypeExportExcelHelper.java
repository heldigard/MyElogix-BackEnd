package com.elogix.api.product.infrastructure.helper;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;
import com.elogix.api.product.domain.model.ProductType;

@Component
public class ProductTypeExportExcelHelper extends GenericExportExcelHelper<ProductType> {

  @Override
  protected String getSheetName() {
    return "ProductTypes";
  }

  @Override
  protected String[] getHeaders() {
    return new String[] {
        "SubCategoria",
        "Categoria"
    };
  }

  @Override
  protected void writeEntityToRow(ProductType productType, Row row) {
    row.createCell(0).setCellValue(productType.getName());
    row.createCell(1).setCellValue(
        productType.getCategory() != null ? productType.getCategory().getName() : "");
  }
}
