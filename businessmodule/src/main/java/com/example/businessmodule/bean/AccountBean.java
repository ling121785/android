package com.example.businessmodule.bean;

import com.example.businessmodule.rest.HttpBaseResult;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ll on 2018/3/26.
 */

public class AccountBean implements Serializable{
    @SerializedName("account")
    private String account;
    @SerializedName("token")
    private String pwd;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("name")
    private String name;
    @SerializedName("icon")
    private String icon;
    @SerializedName("coin")
    private int coin;

    public AccountBean() {
    }

    public AccountBean(String account, String pwd) {
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
