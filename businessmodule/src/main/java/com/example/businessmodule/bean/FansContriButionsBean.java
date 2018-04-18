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
    private String coin;

    @SerializedName("contribute_coin")
    private String contributeCoin;

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

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getContributeCoin() {
        return contributeCoin;
    }

    public void setContributeCoin(String contributeCoin) {
        this.contributeCoin = contributeCoin;
    }
}
