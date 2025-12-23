package com.github.sveldevorls.readtogether.book.dto;

import java.util.Map;

public record BookRatingsResponse(

    float average,
    int count,
    Map<Integer, Integer> distributions
) {}
