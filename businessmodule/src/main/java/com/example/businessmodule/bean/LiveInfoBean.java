package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 2018/4/19.
 */

public class LiveInfoBean {
    @SerializedName("name")
    private String name ;

    @SerializedName("icon")
    private String icon;

    @SerializedName("coin")
    private int coin;

    @SerializedName("status")
    private int status ;

    @SerializedName("live_id")
    private int liveId;

    @SerializedName("room_id")
    private long roomId;

    @SerializedName("live_name")
    private String liveName;

    @SerializedName("live_announcement")
    private String liveAnnouncement;

    @SerializedName("pull_url")
    private String pullUrl;

    @SerializedName("user_coins")
    private String userCoins;

    @SerializedName("start_time")
    private long  startTime;

    @SerializedName("end_time")
    private long endTime;

    @SerializedName("attention_status")
    private int attentionStatus;

    @SerializedName("addrs")
    private String[] address;

    @SerializedName("total_watch_num")
    private int totalWatchNum;

    @SerializedName("is_charge")
    private boolean isCharge;

    @SerializedName("is_private")
    private boolean isPrivate;

    @SerializedName("guard_coin")
    private int guardCoin;

    @SerializedName("is_guard")
    private boolean isGuard;

    @SerializedName("charge")
    private int charge;

    @SerializedName("fans_number")
    private int newFansNum;

    @SerializedName("gift_coin")
    private int giftCoin;

    @SerializedName("like_number")
    private int likeNum;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLiveAnnouncement() {
        return liveAnnouncement;
    }

    public void setLiveAnnouncement(String liveAnnouncement) {
        this.liveAnnouncement = liveAnnouncement;
    }

    public String getPullUrl() {
        return pullUrl;
    }

    public void setPullUrl(String pullUrl) {
        this.pullUrl = pullUrl;
    }

    public String getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(String userCoins) {
        this.userCoins = userCoins;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getAttentionStatus() {
        return attentionStatus;
    }

    public void setAttentionStatus(int attentionStatus) {
        this.attentionStatus = attentionStatus;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public int getTotalWatchNum() {
        return totalWatchNum;
    }

    public void setTotalWatchNum(int totalWatchNum) {
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

    public int getGuardCoin() {
        return guardCoin;
    }

    public void setGuardCoin(int guardCoin) {
        this.guardCoin = guardCoin;
    }

    public boolean isGuard() {
        return isGuard;
    }

    public void setGuard(boolean guard) {
        isGuard = guard;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getNewFansNum() {
        return newFansNum;
    }

    public void setNewFansNum(int newFansNum) {
        this.newFansNum = newFansNum;
    }

    public int getGiftCoin() {
        return giftCoin;
    }

    public void setGiftCoin(int giftCoin) {
        this.giftCoin = giftCoin;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
