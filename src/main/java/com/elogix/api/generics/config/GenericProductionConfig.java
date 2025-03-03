package com.elogix.api.generics.config;

import com.elogix.api.generics.domain.model.GenericProduction;
import com.elogix.api.generics.infrastructure.helpers.GenericProductionMapper;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionData;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionRepository;
import com.elogix.api.shared.infraestructure.notification.NotificationService;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(builderMethodName = "productionBuilder", toBuilder = true)
public class GenericProductionConfig<T extends GenericProduction, D extends GenericProductionData, R extends GenericProductionRepository<D>, M extends GenericProductionMapper<T, D>>
    extends GenericStatusConfig<T, D, R, M> {

  private final NotificationService notificationService;

  public GenericProductionConfig(GenericStatusConfig<T, D, R, M> config, NotificationService notificationService) {
    super(config, config.getStatusUseCase(), config.getStatusMapper());
    this.notificationService = notificationService;
  }
}
