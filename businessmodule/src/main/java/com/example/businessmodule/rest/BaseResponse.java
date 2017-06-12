package com.example.businessmodule.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 2016/6/8.
 */
public class BaseResponse<E> extends BaseResult{

    @SerializedName("result")
    private  E  result;

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }
}
