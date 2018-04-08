package com.example.businessmodule.bean;

import com.example.businessmodule.rest.HttpBaseResult;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/3/31.
 */

public class RoomBean {
    @SerializedName("live_id")
    private String liveId;
    @SerializedName("room_id")
    private String roomId;
    @SerializedName("live_url")
    private String liveUrl;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }
}
