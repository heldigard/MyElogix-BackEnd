package com.tarapaca.api.product_order.dto;

import com.tarapaca.api.product_order.domain.model.ProductOrder;
import com.tarapaca.api.users.domain.model.UserBasic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderRequest {
    private ProductOrder productOrder;
    private UserBasic user;
}
