package com.example.businessmodule.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/3/31.
 */

public class HttpBaseResult {
    @SerializedName("status")
    private int status;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
