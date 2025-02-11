package com.tarapaca.api.generics.infrastructure.repository.GenericProduction;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tarapaca.api.generics.infrastructure.repository.GenericStatus.GenericStatusData;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicData;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Extended entity class that adds production status management
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class GenericProductionData extends GenericStatusData {
    // Status Flags
    @Builder.Default
    @Column(name = "is_pending", columnDefinition = "boolean DEFAULT true")
    private boolean isPending = true;

    @Builder.Default
    @Column(name = "is_production", columnDefinition = "boolean DEFAULT false")
    private boolean isProduction = false;

    @Builder.Default
    @Column(name = "is_finished", columnDefinition = "boolean DEFAULT false")
    private boolean isFinished = false;

    @Builder.Default
    @Column(name = "is_delivered", columnDefinition = "boolean DEFAULT false")
    private boolean isDelivered = false;

    @Builder.Default
    @Column(name = "is_cancelled", columnDefinition = "boolean DEFAULT false")
    private boolean isCancelled = false;

    @Builder.Default
    @Column(name = "is_paused", columnDefinition = "boolean DEFAULT false")
    private boolean isPaused = false;

    // Status Timestamps
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "production_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant productionAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "finished_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant finishedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "delivered_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant deliveredAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "cancelled_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant cancelledAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "paused_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant pausedAt;

    // User Assignments
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_by_id")
    private UserBasicData productionBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_by_id")
    private UserBasicData finishedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivered_by_id")
    private UserBasicData deliveredBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by_id")
    private UserBasicData cancelledBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paused_by_id")
    private UserBasicData pausedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        GenericProductionData that = (GenericProductionData) o;
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

    protected GenericProductionData() {
        super();
    }
}
