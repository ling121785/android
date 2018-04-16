package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 2018/4/6.
 */

public class InComeBean {
    @SerializedName("nick_name")
    private String nick;

    @SerializedName("icon")
    private String icon;

    @SerializedName("type")
    private int type;//消费类别 0 礼物，1收费直播 2守护天使

    @SerializedName("time")
    private long time;

    @SerializedName("coin")
    private int coin;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
