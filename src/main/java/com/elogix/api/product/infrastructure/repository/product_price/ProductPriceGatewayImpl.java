package com.elogix.api.product.infrastructure.repository.product_price;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;

import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.elogix.api.product.domain.gateway.ProductPriceGateway;
import com.elogix.api.product.domain.model.ProductPrice;
import com.elogix.api.product.infrastructure.helper.mapper.ProductPriceMapper;

public class ProductPriceGatewayImpl
        extends GenericGatewayImpl<ProductPrice, ProductPriceData, ProductPriceDataJpaRepository, ProductPriceMapper>
        implements ProductPriceGateway {

    public ProductPriceGatewayImpl(
            GenericConfig<ProductPrice, ProductPriceData, ProductPriceDataJpaRepository, ProductPriceMapper> config) {
        super(config);
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
