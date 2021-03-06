package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 2018/4/16.
 */

public class LiveBean {
    @SerializedName("name")
    private String name;

    @SerializedName("coin")
    private int coin;

    @SerializedName("live_id")
    private String liveId;

    @SerializedName("live_name")
    private String liveName;

    @SerializedName("room_id")
    private String roomId;

    @SerializedName("poster")
    private String poster;

    @SerializedName("start_time")
    private long startTime;

    @SerializedName("end_time")
    private long endTime;

    @SerializedName("watch_num")
    private int watchNum;

    @SerializedName("like_number")
    private int likeNum;

    @SerializedName("fans_numbe")
    private int fansNum;

    @SerializedName("total_watch_num")
    private String totalWatchNum;

    @SerializedName("is_charge")
    private boolean isCharge;

    @SerializedName("is_private")
    private boolean isPrivate;

    private  transient  boolean isCheck=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(int watchNum) {
        this.watchNum = watchNum;
    }

    public String getTotalWatchNum() {
        return totalWatchNum;
    }

    public void setTotalWatchNum(String totalWatchNum) {
        this.totalWatchNum = totalWatchNum;
    }

    public boolean isCharge() {
        return isCharge;
    }

    public void setCharge(boolean charge) {
        isCharge = charge;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
}
