package com.tarapaca.api.product.infrastructure.repository.product_type;

import com.tarapaca.api.product.dto.ProductTypeExcelResponse;
import com.tarapaca.api.product.infrastructure.helper.ProductTypeImportExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ProductTypeExcelService {
    private final ProductTypeDataJpaRepository repository;
    private final ProductTypeImportExcelHelper importExcelHelper;
//    private final ProductTypeExportExcelHelper exportExcelHelper;

    public ProductTypeExcelResponse importExcelFile(MultipartFile file) {
        ProductTypeExcelResponse productTypesResponse = new ProductTypeExcelResponse();
        try {
            productTypesResponse = importExcelHelper.excelToProductTypes(file.getInputStream());
            repository.saveAll(productTypesResponse.getProductTypes());
        } catch (IOException e) {
            productTypesResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return productTypesResponse;
    }

//    TODO
//    public ByteArrayInputStream exportExcelFile() {
//        List<ProductTypeData> productTypes = repository.findAll();
//
//        ByteArrayInputStream in = exportExcelHelper.productTypesToExcel(productTypes);
//        return in;
//    }
}
