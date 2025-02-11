package com.tarapaca.api.shared.domain.model.pagination;

import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.Value;

@Value
public class PaginationCriteria {
    int page;
    int pageSize;
    List<Sort.Order> sortOrders;
}
