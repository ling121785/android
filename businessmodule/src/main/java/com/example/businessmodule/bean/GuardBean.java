package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/21.
 */

public class GuardBean {
    public GuardBean() {
    }
    @SerializedName("nick_name")
    private String  nickName;

    @SerializedName("icon")
    private String icon;

    @SerializedName("guard_coin")
    private int guardCoin;

    @SerializedName("next_guard_coin")
    private int nexGuardCpin;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getGuardCoin() {
        return guardCoin;
    }

    public void setGuardCoin(int guardCoin) {
        this.guardCoin = guardCoin;
    }

    public int getNexGuardCpin() {
        return nexGuardCpin;
    }

    public void setNexGuardCpin(int nexGuardCpin) {
        this.nexGuardCpin = nexGuardCpin;
    }
}
