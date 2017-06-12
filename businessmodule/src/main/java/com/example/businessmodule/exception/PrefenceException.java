package com.example.businessmodule.exception;

/**
 * 配置相关异常
 */
public class PrefenceException extends Exception {

    private String message;

    public PrefenceException(String ErrorMessagr) {
        message = ErrorMessagr;
    }

    public String getMessage() {
        return message;
    }
}
