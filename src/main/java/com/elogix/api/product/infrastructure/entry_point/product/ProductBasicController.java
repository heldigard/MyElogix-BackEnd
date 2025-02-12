package com.elogix.api.product.infrastructure.entry_point.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.generics.infrastructure.entry_points.GenericBasicController;
import com.elogix.api.product.domain.gateway.ProductBasicGateway;
import com.elogix.api.product.domain.model.ProductBasic;
import com.elogix.api.product.domain.usecase.ProductBasicUseCase;

@RestController
@RequestMapping("/api/v1/productBasic")
public class ProductBasicController
        extends GenericBasicController<ProductBasic, ProductBasicGateway, ProductBasicUseCase> {

    public ProductBasicController(ProductBasicUseCase useCase) {
        super(useCase);
    }

    @GetMapping(value = "/find/reference")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public ResponseEntity<ProductBasic> findByReference(
            @RequestParam String reference,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        ProductBasic product = useCase.findByReference(reference, isDeleted);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
