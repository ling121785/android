package com.example.businessmodule.exception;

/**
 * 配置相关异常
 */
public class UrlEncodeException extends Exception {

    private String message;

    public UrlEncodeException(String ErrorMessagr) {
        message = ErrorMessagr;
    }

    public String getMessage() {
        return message;
    }
}
