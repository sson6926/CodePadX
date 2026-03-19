package com.shawnix.codepadx.dto.response;

import java.util.List;

public class PaginationResponse<T> {
    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
