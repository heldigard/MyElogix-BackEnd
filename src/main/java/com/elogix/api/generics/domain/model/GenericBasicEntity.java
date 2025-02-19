package com.elogix.api.generics.domain.model;

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
    @JsonProperty(value = "isDeleted", defaultValue = "false")
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
        if (this.id == null || that.id == null)
            return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : super.hashCode();
    }

    @Override
    public String toString() {
        return "id=" + (id != null ? id : "null") +
                ", version=" + version +
                ", isDeleted=" + isDeleted;
    }
}
