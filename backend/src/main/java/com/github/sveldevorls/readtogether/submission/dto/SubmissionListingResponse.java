package com.github.sveldevorls.readtogether.submission.dto;

import java.util.List;

public record SubmissionListingResponse<T>(
    List<T> submissions,
    int totalCount,
    int totalPages,
    int currentPage,
    int pageSize
) {}
