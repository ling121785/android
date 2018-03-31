package com.example.businessmodule.exception;

import com.example.businessmodule.rest.HttpBaseResult;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/3/31.
 */

public class ResponseException extends Exception{
    private String message;

    public ResponseException(String ErrorMessagr) {
        message = ErrorMessagr;
    }

    public String getMessage() {
        return message;
    }
}
