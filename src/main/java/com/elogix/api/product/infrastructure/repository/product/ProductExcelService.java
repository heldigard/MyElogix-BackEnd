package com.elogix.api.product.infrastructure.repository.product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product.dto.ProductExcelResponse;
import com.elogix.api.product.infrastructure.helper.ProductExportExcelHelper;
import com.elogix.api.product.infrastructure.helper.ProductImportExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductExcelService {
    private final ProductUseCase useCase;
    private final ProductImportExcelHelper importExcelHelper;
    private final ProductExportExcelHelper exportExcelHelper;

    public ProductExcelResponse importExcelFile(MultipartFile file) {
        ProductExcelResponse productsResponse = new ProductExcelResponse();
        try {
            productsResponse = importExcelHelper.excelToProducts(file.getInputStream());
            useCase.saveAll(productsResponse.getProducts());
        } catch (IOException e) {
            productsResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return productsResponse;
    }

    public ByteArrayInputStream exportExcelFile() {
        List<String> properties = List.of("reference");
        List<String> directions = List.of("asc");
        List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
        List<Product> existingList = useCase.findAll(sortOrders, false);

        ByteArrayInputStream in = exportExcelHelper.productsToExcel(existingList);
        return in;
    }
}
