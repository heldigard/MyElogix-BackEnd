package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person;

import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface ContactPersonDataJpaRepository
        extends GenericNamedRepository<ContactPersonData> {
}
