package com.example.businessmodule.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 2016/6/8.
 */
public class BaseResult {
    public static final int RESULT_SUCCESS=0;
    public static final int RESULT_ERROR=-1;


    @SerializedName("ret")
    private int ret= RESULT_ERROR;

    @SerializedName("code")
    private String errorCode;

    @SerializedName("desc")
    private String errorMsg;




    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public boolean isSuccess(){
        return ret==BaseResult.RESULT_SUCCESS;
    }
}
