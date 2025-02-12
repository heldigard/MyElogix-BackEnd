package com.elogix.api.generics.infrastructure.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Generic pagination response wrapper
 *
 * @param <T> Type of items in the response
 */
@Builder
@Getter
@Setter
public class PaginationResponse<T> {
    private List<T> rows;
    private Long rowCount;
    private Integer pagesCount;
    private Integer currentPage;
    private boolean success;
    private String message;

    public PaginationResponse(List<T> rows, Long rowCount, Integer pagesCount, Integer currentPage) {
        this.rows = rows;
        this.rowCount = rowCount;
        this.pagesCount = pagesCount;
        this.currentPage = currentPage;
        this.success = false;
        this.message = null;
    }

    public PaginationResponse(List<T> rows, Long rowCount, Integer pagesCount, Integer currentPage, boolean success) {
        this.rows = rows;
        this.rowCount = rowCount;
        this.pagesCount = pagesCount;
        this.currentPage = currentPage;
        this.success = success;
        this.message = null;
    }

    public PaginationResponse(List<T> rows, Long rowCount, Integer pagesCount, Integer currentPage, boolean success,
            String message) {
        this.rows = rows;
        this.rowCount = rowCount;
        this.pagesCount = pagesCount;
        this.currentPage = currentPage;
        this.success = success;
        this.message = message;
    }
}
