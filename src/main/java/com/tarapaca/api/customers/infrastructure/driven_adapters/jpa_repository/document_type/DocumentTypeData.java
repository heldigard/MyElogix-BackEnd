package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.document_type;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "document_types")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE document_types SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedDocumentTypeFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedDocumentTypeFilter", condition = "is_deleted = :isDeleted")
public class DocumentTypeData extends GenericNamedData {
    @NotBlank
    @Column(length = 5, nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        DocumentTypeData that = (DocumentTypeData) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public DocumentTypeData() {
        super();
    }
}
