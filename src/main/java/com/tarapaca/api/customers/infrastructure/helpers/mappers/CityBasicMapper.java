package com.tarapaca.api.customers.infrastructure.helpers.mappers;

import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.domain.model.CityBasic;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericNamedBasicMapper;

@Component
public class CityBasicMapper
        extends GenericNamedBasicMapper<CityBasic, CityBasicData> {

    public CityBasicMapper() {
        super(CityBasic.class, CityBasicData.class);
    }

}
