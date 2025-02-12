package com.elogix.api.product.infrastructure.entry_point.product_category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.generics.infrastructure.entry_points.GenericNamedController;
import com.elogix.api.product.domain.gateway.ProductCategoryGateway;
import com.elogix.api.product.domain.model.ProductCategory;
import com.elogix.api.product.domain.usecase.ProductCategoryUseCase;

@RestController
@RequestMapping("/api/v1/product-category")
public class ProductCategoryController
        extends GenericNamedController<ProductCategory, ProductCategoryGateway, ProductCategoryUseCase> {
    public ProductCategoryController(ProductCategoryUseCase useCase) {
        super(useCase);
    }
}
