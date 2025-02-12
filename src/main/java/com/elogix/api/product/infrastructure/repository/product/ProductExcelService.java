package com.elogix.api.product.infrastructure.repository.product;

import com.elogix.api.product.dto.ProductExcelResponse;
import com.elogix.api.product.infrastructure.helper.ProductExportExcelHelper;
import com.elogix.api.product.infrastructure.helper.ProductImportExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductExcelService {
    private final ProductDataJpaRepository repository;
    private final ProductImportExcelHelper importExcelHelper;
    private final ProductExportExcelHelper exportExcelHelper;

    public ProductExcelResponse importExcelFile(MultipartFile file) {
        ProductExcelResponse productsResponse = new ProductExcelResponse();
        try {
            productsResponse = importExcelHelper.excelToProducts(file.getInputStream());
            repository.saveAll(productsResponse.getProducts());
        } catch (IOException e) {
            productsResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return productsResponse;
    }

    public ByteArrayInputStream exportExcelFile() {
        List<ProductData> products = repository.findAll();

        ByteArrayInputStream in = exportExcelHelper.productsToExcel(products);
        return in;
    }
}
