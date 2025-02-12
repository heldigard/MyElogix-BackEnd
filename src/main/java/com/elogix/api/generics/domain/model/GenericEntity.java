package com.elogix.api.generics.domain.model;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.elogix.api.users.domain.model.UserBasic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Extended domain entity class that adds audit capabilities
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public abstract class GenericEntity extends GenericBasicEntity {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant deletedAt;

    private UserBasic createdBy;
    private UserBasic updatedBy;
    private UserBasic deletedBy;

    protected GenericEntity() {
        super();
        this.createdAt = Instant.now();
    }

    protected GenericEntity(Long id) {
        super(id);
        this.createdAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        GenericEntity that = (GenericEntity) o;
        return Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(deletedAt, that.deletedAt) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(deletedBy, that.deletedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), createdAt, updatedAt, deletedAt, createdBy, updatedBy, deletedBy);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", createdBy=" + (createdBy != null ? createdBy.getId() : null) +
                ", updatedBy=" + (updatedBy != null ? updatedBy.getId() : null) +
                ", deletedBy=" + (deletedBy != null ? deletedBy.getId() : null);
    }
}
