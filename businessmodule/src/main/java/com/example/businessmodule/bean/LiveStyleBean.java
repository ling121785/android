package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/21.
 */

public class LiveStyleBean {
    @SerializedName("id")
    private int id;

    @SerializedName("live_style")
    private String liveStyle;

    @SerializedName("live_style_name")
    private String liveStyleName;

    @SerializedName("create_time")
    private long createTime;

    @SerializedName("update_time")
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLiveStyle() {
        return liveStyle;
    }

    public void setLiveStyle(String liveStyle) {
        this.liveStyle = liveStyle;
    }

    public String getLiveStyleName() {
        return liveStyleName;
    }

    public void setLiveStyleName(String liveStyleName) {
        this.liveStyleName = liveStyleName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
