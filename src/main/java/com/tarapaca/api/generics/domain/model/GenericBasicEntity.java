package com.tarapaca.api.generics.domain.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Base domain entity class that provides common fields and functionality
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public abstract class GenericBasicEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    @Version
    @Builder.Default
    @JsonProperty("version")
    private Long version = 0L;

    private Long id;

    @Builder.Default
    @JsonProperty("isDeleted")
    private boolean isDeleted = false;

    protected GenericBasicEntity() {
        this.version = 0L;
        this.isDeleted = false;
    }

    protected GenericBasicEntity(Long id) {
        this();
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenericBasicEntity that))
            return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + id +
                ", version=" + version +
                ", isDeleted=" + isDeleted + ")";
    }
}
