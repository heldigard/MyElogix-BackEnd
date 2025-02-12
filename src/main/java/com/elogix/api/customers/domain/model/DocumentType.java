package com.elogix.api.customers.domain.model;

import com.elogix.api.generics.domain.model.GenericNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData}
 */

@Getter
@Setter
@SuperBuilder
public class DocumentType extends GenericNamed {
    public DocumentType() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DocumentType that))
            return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
