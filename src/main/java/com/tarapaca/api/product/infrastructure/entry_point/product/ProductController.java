package com.tarapaca.api.product.infrastructure.entry_point.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.generics.infrastructure.dto.ApiResponse;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericStatusController;
import com.tarapaca.api.product.domain.gateway.ProductGateway;
import com.tarapaca.api.product.domain.model.Product;
import com.tarapaca.api.product.domain.usecase.ProductUseCase;
import com.tarapaca.api.shared.domain.model.Hits;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController
        extends GenericStatusController<Product, ProductGateway, ProductUseCase> {

    public ProductController(ProductUseCase useCase) {
        super(useCase);
    }

    @DeleteMapping("/reference")
    public ResponseEntity<?> deleteByReference(@RequestParam String reference) {
        useCase.deleteByReference(reference);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/reference/{reference}")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public ResponseEntity<ApiResponse<Product>> findByReference(
            @PathVariable String reference,
            @RequestParam(defaultValue = "0") boolean includeDeleted) {
        Product product = useCase.findByReference(reference, includeDeleted);
        if (product == null) {
            return ResponseEntity.ok(
                    new ApiResponse<Product>("Product not found"));
        }

        return ResponseEntity.ok(new ApiResponse<>("Product found", product));
    }

    @PutMapping("/hits")
    public ResponseEntity<Integer> updateHits(@RequestBody Hits hits) {
        int response = useCase.updateHits(hits);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/increment/hits")
    public ResponseEntity<Integer> incrementHits(@RequestBody Hits hits) {
        int response = useCase.incrementHits(hits);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
