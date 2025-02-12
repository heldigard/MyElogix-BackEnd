package com.elogix.api.delivery_order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrdersSummaryDTO {
        private Long customerId;
        private Long deliveryOrdersCount;
        private String day;
        private String customerName;
        private String deliveryZoneName;
        private Boolean isBilled;
        private String deliveryOrderIds;

        public CustomerOrdersSummaryDTO() {
        }

        public CustomerOrdersSummaryDTO(
                        Long customerId,
                        Long deliveryOrdersCount,
                        String day,
                        String customerName,
                        String deliveryZoneName,
                        Boolean isBilled,
                        String deliveryOrderIds) {
                this.customerId = customerId;
                this.deliveryOrdersCount = deliveryOrdersCount;
                this.day = day;
                this.customerName = customerName;
                this.deliveryZoneName = deliveryZoneName;
                this.isBilled = isBilled;
                this.deliveryOrderIds = deliveryOrderIds;
        }
}
