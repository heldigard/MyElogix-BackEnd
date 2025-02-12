package com.elogix.api.delivery_orders.domain.model;

public enum EStatus {
    DRAFT,
    PENDING,
    PRODUCTION,
    FINISHED,
    DELIVERED,
    CANCELLED,
    EXIST,
    LOW_STOCK,
    OUT_OF_STOCK,
    NEW,
    PAUSED,
}
