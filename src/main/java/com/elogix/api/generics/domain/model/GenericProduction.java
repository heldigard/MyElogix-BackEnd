package com.elogix.api.generics.domain.model;

import java.time.Instant;
import java.util.Objects;

import com.elogix.api.users.domain.model.UserBasic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class GenericProduction extends GenericStatus {
    // Status Flags
    @Builder.Default
    @JsonProperty("isPending")
    private boolean isPending = true;

    @Builder.Default
    @JsonProperty("isProduction")
    private boolean isProduction = false;

    @Builder.Default
    @JsonProperty("isFinished")
    private boolean isFinished = false;

    @Builder.Default
    @JsonProperty("isDelivered")
    private boolean isDelivered = false;

    @Builder.Default
    @JsonProperty("isCancelled")
    private boolean isCancelled = false;

    @Builder.Default
    @JsonProperty("isPaused")
    private boolean isPaused = false;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant productionAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant finishedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant deliveredAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant cancelledAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant pausedAt;

    // User Assignments
    private UserBasic productionBy;
    private UserBasic finishedBy;
    private UserBasic deliveredBy;
    private UserBasic cancelledBy;
    private UserBasic pausedBy;

    protected GenericProduction() {
        super();
        this.isPending = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenericProduction))
            return false;
        if (!super.equals(o))
            return false;
        GenericProduction that = (GenericProduction) o;
        return isPending == that.isPending &&
                isProduction == that.isProduction &&
                isFinished == that.isFinished &&
                isDelivered == that.isDelivered &&
                isCancelled == that.isCancelled &&
                isPaused == that.isPaused &&
                Objects.equals(productionAt, that.productionAt) &&
                Objects.equals(finishedAt, that.finishedAt) &&
                Objects.equals(deliveredAt, that.deliveredAt) &&
                Objects.equals(cancelledAt, that.cancelledAt) &&
                Objects.equals(pausedAt, that.pausedAt) &&
                Objects.equals(productionBy, that.productionBy) &&
                Objects.equals(finishedBy, that.finishedBy) &&
                Objects.equals(deliveredBy, that.deliveredBy) &&
                Objects.equals(cancelledBy, that.cancelledBy) &&
                Objects.equals(pausedBy, that.pausedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isPending, isProduction, isFinished, isDelivered, isCancelled, isPaused,
                productionAt, finishedAt, deliveredAt, cancelledAt, pausedAt, productionBy, finishedBy, deliveredBy,
                cancelledBy, pausedBy);
    }

    @Override
    public String toString() {
        return super.toString() +
        // Status flags
                ", isPending=" + isPending +
                ", isProduction=" + isProduction +
                ", isFinished=" + isFinished +
                ", isDelivered=" + isDelivered +
                ", isCancelled=" + isCancelled +
                ", isPaused=" + isPaused +
                // Timestamps
                ", productionAt=" + productionAt +
                ", finishedAt=" + finishedAt +
                ", deliveredAt=" + deliveredAt +
                ", cancelledAt=" + cancelledAt +
                ", pausedAt=" + pausedAt +
                // Users
                ", productionBy=" + (productionBy != null ? productionBy.getId() : "null") +
                ", finishedBy=" + (finishedBy != null ? finishedBy.getId() : "null") +
                ", deliveredBy=" + (deliveredBy != null ? deliveredBy.getId() : "null") +
                ", cancelledBy=" + (cancelledBy != null ? cancelledBy.getId() : "null") +
                ", pausedBy=" + (pausedBy != null ? pausedBy.getId() : "null");
    }
}
