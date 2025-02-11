package com.tarapaca.api.product.infrastructure.entry_point.product_type;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedController;
import com.tarapaca.api.product.domain.gateway.ProductTypeGateway;
import com.tarapaca.api.product.domain.model.ProductType;
import com.tarapaca.api.product.domain.usecase.ProductTypeUseCase;

@RestController
@RequestMapping("/api/v1/product-type")
public class ProductTypeController extends GenericNamedController<ProductType, ProductTypeGateway, ProductTypeUseCase> {

    public ProductTypeController(ProductTypeUseCase useCase) {
        super(useCase);
    }

    @PutMapping("/update/isMeasurable")
    public ResponseEntity<ProductType> updateIsMeasurable(@RequestParam Long id,
            @RequestParam boolean isMeasurable) {
        ProductType updated = useCase.updateIsMeasurable(id, isMeasurable);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/update/category")
    public ResponseEntity<ProductType> updateCategory(
            @RequestParam String name,
            @RequestParam Long id) {
        ProductType updated = useCase.updateCategory(name, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping(value = "/find/category")
    public ResponseEntity<List<ProductType>> findByCategory(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        List<ProductType> productTypeList = useCase.findByCategory(name, isDeleted);

        return new ResponseEntity<>(productTypeList, HttpStatus.OK);
    }
}
