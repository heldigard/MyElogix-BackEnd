package com.tarapaca.api.product.infrastructure.repository.product_basic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.data.domain.Sort;

import com.tarapaca.api.generics.infrastructure.repository.GenericBasic.GenericBasicGatewayImpl;
import com.tarapaca.api.product.domain.gateway.ProductBasicGateway;
import com.tarapaca.api.product.domain.model.ProductBasic;
import com.tarapaca.api.product.infrastructure.helper.mapper.ProductBasicMapper;

import jakarta.persistence.EntityManager;

public class ProductBasicGatewayImpl
        extends
        GenericBasicGatewayImpl<ProductBasic, ProductBasicData, ProductBasicDataJpaRepository, ProductBasicMapper>
        implements ProductBasicGateway {

    public ProductBasicGatewayImpl(
            ProductBasicDataJpaRepository repository,
            ProductBasicMapper mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
    }

    @Override
    public ProductBasic findByReference(String reference, boolean isDeleted)
            throws IllegalArgumentException, NoSuchElementException {
        Session session = setDeleteFilter(isDeleted);
        Optional<ProductBasicData> productBasicData = repository.findByReference(reference);
        session.disableFilter(this.deletedFilter);

        return mapper.toDomain(productBasicData.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<ProductBasic> findAll(
            List<Sort.Order> sortOrders,
            boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        Sort sort = Sort.by(sortOrders);

        List<ProductBasicData> productDataList = repository.findAll(sort);
        session.disableFilter(this.deletedFilter);

        return productDataList.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
