package com.elogix.api.customers.infrastructure.entry_points.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;

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
public class NeighborhoodExcelResponse {
    @Builder.Default
    List<NeighborhoodData> neighborhoods = new ArrayList<NeighborhoodData>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
