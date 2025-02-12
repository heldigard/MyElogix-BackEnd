package com.elogix.api.generics.infrastructure.dto;

import java.util.List;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.generics.domain.model.GenericProduction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusTransitionResponse<T extends GenericProduction> extends ApiResponse<T> {
    private EStatus currentStatus;
    private EStatus targetStatus;

    public StatusTransitionResponse() {
        super();
    }

    public StatusTransitionResponse(String message) {
        super(message);
    }

    public StatusTransitionResponse(EStatus currentStatus, EStatus targetStatus) {
        super();
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    public StatusTransitionResponse(String message, EStatus currentStatus, EStatus targetStatus) {
        super(message);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    public StatusTransitionResponse(EStatus currentStatus, EStatus targetStatus, T item) {
        super("", item);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    public StatusTransitionResponse(EStatus currentStatus, EStatus targetStatus, List<T> items) {
        super("", items);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    public StatusTransitionResponse(String message, EStatus currentStatus, EStatus targetStatus, T item) {
        super(message, item);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    public StatusTransitionResponse(String message, EStatus currentStatus, EStatus targetStatus, List<T> items) {
        super(message, items);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    public T getOrder() {
        return getData().getItem();
    }

    public List<T> getOrders() {
        return getData().getItems();
    }

    public void setOrder(T order) {
        getData().setItem(order);
    }

    public void setOrder(List<T> orders) {
        getData().setItems(orders);
    }
}
