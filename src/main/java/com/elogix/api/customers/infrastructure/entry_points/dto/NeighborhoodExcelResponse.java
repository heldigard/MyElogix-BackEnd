package com.elogix.api.customers.infrastructure.entry_points.dto;

import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.generics.infrastructure.dto.ExcelResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class NeighborhoodExcelResponse extends ExcelResponse<Neighborhood> {
}
