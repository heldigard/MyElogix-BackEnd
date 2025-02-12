package com.elogix.api.customers.infrastructure.entry_points.dto;

import java.util.HashSet;
import java.util.Set;

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerExcelResponse {
    @Builder.Default
    Set<CustomerData> customers = new HashSet<>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
