package com.tarapaca.api.customers.domain.model;

import com.tarapaca.api.generics.domain.model.GenericNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData}
 */

@Getter
@Setter
@SuperBuilder
public class DocumentType extends GenericNamed {
    public DocumentType() {
        super();
    }
}
