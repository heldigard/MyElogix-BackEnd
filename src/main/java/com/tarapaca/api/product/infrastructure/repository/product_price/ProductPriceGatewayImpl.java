package com.tarapaca.api.product.infrastructure.repository.product_price;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;

import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.tarapaca.api.product.domain.gateway.ProductPriceGateway;
import com.tarapaca.api.product.domain.model.ProductPrice;
import com.tarapaca.api.product.infrastructure.helper.mapper.ProductPriceMapper;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class ProductPriceGatewayImpl
        extends GenericGatewayImpl<ProductPrice, ProductPriceData, ProductPriceDataJpaRepository, ProductPriceMapper>
        implements ProductPriceGateway {

    public ProductPriceGatewayImpl(
            ProductPriceDataJpaRepository repository,
            ProductPriceMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }

    @Override
    public void deleteByProductRef(String productRef) {
        repository.deleteByProductRef(productRef);
    }

    @Override
    public ProductPrice findByProductRef(String productRef, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        Optional<ProductPriceData> productPriceData = repository.findByProductRef(productRef);
        session.disableFilter(this.deletedFilter);

        return mapper.toDomain(productPriceData.orElseThrow(NoSuchElementException::new));
    }
}
