package com.example.businessmodule.rest;

/**
 * Http method
 */
public enum RestApiMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),

    NONE("NONE");

    private String name;

    RestApiMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "method = " + name;
    }
}
