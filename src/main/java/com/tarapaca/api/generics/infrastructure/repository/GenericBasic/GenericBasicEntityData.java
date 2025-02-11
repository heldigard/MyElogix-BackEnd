package com.tarapaca.api.generics.infrastructure.repository.GenericBasic;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Base entity class that provides common fields and functionality for all
 * entities
 * Includes version control, ID generation and soft delete capabilities
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@MappedSuperclass
public abstract class GenericBasicEntityData implements Serializable {

    @Version
    @Column(name = "version", columnDefinition = "bigint DEFAULT 0", nullable = false)
    @JsonProperty("version")
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Builder.Default
    @JsonProperty("isDeleted")
    @Column(name = "is_deleted", columnDefinition = "boolean DEFAULT false")
    private boolean isDeleted = false;

    protected GenericBasicEntityData() {
        this.isDeleted = false;
        this.version = 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GenericBasicEntityData that = (GenericBasicEntityData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + id + ", version=" + version + ", isDeleted=" + isDeleted + ")";
    }
}
