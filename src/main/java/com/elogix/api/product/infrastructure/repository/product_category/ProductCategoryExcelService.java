package com.elogix.api.product.infrastructure.repository.product_category;

import com.elogix.api.product.dto.ProductCategoryExcelResponse;
import com.elogix.api.product.infrastructure.helper.ProductCategoryImportExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ProductCategoryExcelService {
    private final ProductCategoryDataJpaRepository repository;
    private final ProductCategoryImportExcelHelper importExcelHelper;
//    private final ProductCategoryExportExcelHelper exportExcelHelper;

    public ProductCategoryExcelResponse importExcelFile(MultipartFile file) {
        ProductCategoryExcelResponse productCategoriesResponse = new ProductCategoryExcelResponse();
        try {
            productCategoriesResponse = importExcelHelper.excelToProductCategories(file.getInputStream());
            repository.saveAll(productCategoriesResponse.getProductCategories());
        } catch (IOException e) {
            productCategoriesResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return productCategoriesResponse;
    }

//    TODO
//    public ByteArrayInputStream exportExcelFile() {
//        List<ProductCategoryData> productCategories = repository.findAll();
//
//        ByteArrayInputStream in = exportExcelHelper.productCategoriesToExcel(productCategories);
//        return in;
//    }
}
