package com.github.sveldevorls.readtogether.util;

import java.util.Map;

public class ErrorMapper {

    public static Map<String, String> map(String field, String message) {
        return Map.of("field", field, "message", message);
    }
}
