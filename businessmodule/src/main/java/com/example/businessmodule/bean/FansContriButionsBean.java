package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 2018/4/15.
 */

public class FansContriButionsBean {
    @SerializedName("icon")
    private  String icon;

    @SerializedName("nick_name")
    private String nick;

    @SerializedName("coin")
    private int coin;

    @SerializedName("contribute_coin")
    private int contributeCoin;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getContributeCoin() {
        return contributeCoin;
    }

    public void setContributeCoin(int contributeCoin) {
        this.contributeCoin = contributeCoin;
    }
}
