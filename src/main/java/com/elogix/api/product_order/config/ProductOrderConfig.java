package com.elogix.api.product_order.config;

import com.elogix.api.generics.config.GenericProductionConfig;
import com.elogix.api.generics.domain.model.GenericProduction;
import com.elogix.api.generics.infrastructure.helpers.GenericProductionMapper;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionData;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionRepository;
import com.elogix.api.product.domain.usecase.ProductUseCase;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(builderMethodName = "productOrderBuilder", toBuilder = true)
public class ProductOrderConfig<T extends GenericProduction, D extends GenericProductionData, R extends GenericProductionRepository<D>, M extends GenericProductionMapper<T, D>>
    extends GenericProductionConfig<T, D, R, M> {

  private final ProductUseCase productUseCase;

  public ProductOrderConfig(GenericProductionConfig<T, D, R, M> config, ProductUseCase productUseCase) {
    super(config, config.getNotificationService());
    this.productUseCase = productUseCase;
  }
}
