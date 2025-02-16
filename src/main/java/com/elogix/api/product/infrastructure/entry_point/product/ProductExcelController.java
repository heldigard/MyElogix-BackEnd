package com.elogix.api.product.infrastructure.entry_point.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.generics.infrastructure.entry_points.GenericExcelController;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.dto.ProductExcelResponse;
import com.elogix.api.product.infrastructure.repository.product.ProductExcelService;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

@RestController
@RequestMapping("/api/v1/product/excel")
public class ProductExcelController extends GenericExcelController<Product, ProductExcelResponse> {

    public ProductExcelController(
            ExcelHelper excelHelper,
            ProductExcelService excelService) {
        super(excelHelper, excelService);
    }

    @Override
    protected String getDownloadFilename() {
        return "products.xlsx";
    }
}
