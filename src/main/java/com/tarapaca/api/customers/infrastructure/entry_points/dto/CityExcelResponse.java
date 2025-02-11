package com.tarapaca.api.customers.infrastructure.entry_points.dto;

import java.util.HashSet;
import java.util.Set;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData;

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
public class CityExcelResponse {
    @Builder.Default
    Set<CityData> cities = new HashSet<>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
