package com.elogix.api.product.infrastructure.entry_point.product_type;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.generics.infrastructure.entry_points.GenericExcelController;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.dto.ProductTypeExcelResponse;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeExcelService;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

@RestController
@RequestMapping("/api/v1/product-type/excel")
public class ProductTypeExcelController extends GenericExcelController<ProductType, ProductTypeExcelResponse> {

    public ProductTypeExcelController(
            ExcelHelper excelHelper,
            ProductTypeExcelService excelService) {
        super(excelHelper, excelService);
    }

    @Override
    protected String getDownloadFilename() {
        return "product-types.xlsx";
    }
}
