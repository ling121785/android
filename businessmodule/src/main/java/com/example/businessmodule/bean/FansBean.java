package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 2018/4/16.
 */

public class FansBean {
    @SerializedName("nick_name")
    private String nick;

    @SerializedName("icon")
    private String icon;

    @SerializedName("is_guard")
    private boolean isGuard;

    @SerializedName("total_coin")
    private int totalCoin;

    @SerializedName("total_watch")
    private int totalWatch;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isGuard() {
        return isGuard;
    }

    public void setGuard(boolean guard) {
        isGuard = guard;
    }

    public int getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(int totalCoin) {
        this.totalCoin = totalCoin;
    }

    public int getTotalWatch() {
        return totalWatch;
    }

    public void setTotalWatch(int totalWatch) {
        this.totalWatch = totalWatch;
    }
}
