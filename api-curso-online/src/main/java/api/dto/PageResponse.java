package api.dto;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    long totalElements,
    long totalPages,
    long size,
    long page
) {}
