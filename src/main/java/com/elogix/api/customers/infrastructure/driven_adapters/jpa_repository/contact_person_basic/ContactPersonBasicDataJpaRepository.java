package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicRepository;

@Repository
public interface ContactPersonBasicDataJpaRepository
        extends GenericNamedBasicRepository<ContactPersonBasicData> {
}
