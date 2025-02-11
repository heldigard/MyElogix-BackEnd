package com.tarapaca.api.product.infrastructure.entry_point.product_category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedController;
import com.tarapaca.api.product.domain.gateway.ProductCategoryGateway;
import com.tarapaca.api.product.domain.model.ProductCategory;
import com.tarapaca.api.product.domain.usecase.ProductCategoryUseCase;

@RestController
@RequestMapping("/api/v1/product-category")
public class ProductCategoryController
        extends GenericNamedController<ProductCategory, ProductCategoryGateway, ProductCategoryUseCase> {
    public ProductCategoryController(ProductCategoryUseCase useCase) {
        super(useCase);
    }
}
