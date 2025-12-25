package com.github.sveldevorls.readtogether.review.dto;

import java.util.Map;

public record RatingsSummary(

    float average,
    int count,
    Map<Integer, Integer> distributions
) {}
