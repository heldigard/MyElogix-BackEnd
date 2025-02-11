package com.tarapaca.api.delivery_order.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Add this annotation for default constructor
public class DeliveryOrderIdListRequest {
    private ArrayList<Long> idList;
    private String sortBy;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @Builder
    public DeliveryOrderIdListRequest(ArrayList<Long> idList, String sortBy, boolean isDeleted) {
        this.idList = idList;
        this.sortBy = sortBy;
        this.isDeleted = isDeleted;
    }
}
