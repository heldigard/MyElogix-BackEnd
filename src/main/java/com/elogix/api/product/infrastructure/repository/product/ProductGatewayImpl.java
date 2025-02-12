package com.elogix.api.product.infrastructure.repository.product;

import org.hibernate.Session;

import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.infrastructure.repository.GenericStatus.GenericStatusGatewayImpl;
import com.elogix.api.product.domain.gateway.ProductGateway;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.infrastructure.exception.ProductNotFoundException;
import com.elogix.api.product.infrastructure.helper.mapper.ProductMapper;
import com.elogix.api.shared.domain.model.Hits;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductGatewayImpl
        extends GenericStatusGatewayImpl<Product, ProductData, ProductDataJpaRepository, ProductMapper>
        implements ProductGateway {
    private final ProductTypeUseCase productTypeUseCase;
    private final StatusUseCase statusUseCase;

    public ProductGatewayImpl(
            ProductDataJpaRepository repository,
            ProductMapper mapper,
            ProductTypeUseCase productTypeUseCase,
            StatusUseCase statusUseCase,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(
                repository,
                mapper,
                entityManager,
                updateUtils,
                statusUseCase,
                deletedFilter);
        this.productTypeUseCase = productTypeUseCase;
        this.statusUseCase = statusUseCase;
    }

    @Override
    public Product update(Product product) {
        Product existing = findById(product.getId(), false);
        // Update fields with a single method call for clarity
        updateNonNullFields(existing, product);

        return save(existing);
    }

    private void updateNonNullFields(Product existing, Product product) {
        updateUtils.updateIfNotEmptyAndNotEqual(
                product.getDescription().toUpperCase(),
                existing::setDescription,
                existing.getDescription());

        if (product.getType() != null && product.getType().getId() != -1) {
            ProductType productType = productTypeUseCase.findById(product.getType().getId(), false);
            existing.setType(productType);
            // Update isMeasurable only if the type has changed to avoid unnecessary updates
            if (existing.getType().isMeasurable() != product.getType().isMeasurable()) {
                productType.setMeasurable(product.getType().isMeasurable());
                productTypeUseCase.save(productType);
            }
        }
        if (product.getStatus() != null && product.getStatus().getId() != -1) {
            Status status = statusUseCase.findById(product.getStatus().getId(), false);
            existing.setStatus(status);
        }
    }

    @Override
    public void deleteByReference(String reference) {
        repository.deleteByReference(reference);
    }

    @Override
    public Product findByReference(String reference, boolean isDeleted) {
        ProductData productData;
        Session session = setDeleteFilter(isDeleted);
        try {
            productData = repository.findByReference(reference)
                    .orElseThrow(() -> new ProductNotFoundException(reference));
            session.disableFilter(this.deletedFilter);
        } finally {
            session.close();
        }

        return mapper.toDomain(productData);
    }

    @Override
    public int updateHits(Hits hits) {
        return repository.updateHits(hits.getId(), hits.getAmount());
    }

    @Override
    public int incrementHits(Hits hits) {
        Product product = this.findById(hits.getId(), false);
        if (product.getId() != null) {
            Long amount = product.getHits();
            if (hits.isIncrease()) {
                amount++;
            } else {
                amount--;
            }
            return repository.updateHits(hits.getId(), amount);
        }
        return 0;
    }

    @Override
    public void incrementOneHit(Long id) {
        Hits hits = Hits.builder()
                .id(id)
                .amount(1L)
                .isIncrease(true)
                .build();
        this.incrementHits(hits);
    }
}
