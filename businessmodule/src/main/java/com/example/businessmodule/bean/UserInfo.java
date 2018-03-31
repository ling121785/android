package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ll on 2018/3/26.
 */

public class UserInfo implements Serializable{
    @SerializedName("account")
    private String account;
    @SerializedName("token")
    private String pwd;

    public UserInfo(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
