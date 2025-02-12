package com.elogix.api.customers.infrastructure.helpers.mappers;

import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.CityBasic;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedBasicMapper;

@Component
public class CityBasicMapper
        extends GenericNamedBasicMapper<CityBasic, CityBasicData> {

    public CityBasicMapper() {
        super(CityBasic.class, CityBasicData.class);
    }

}
