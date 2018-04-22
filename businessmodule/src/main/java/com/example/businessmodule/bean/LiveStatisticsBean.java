package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 2018/4/18.
 */

public class LiveStatisticsBean {
    @SerializedName("live_number")
    private int liveCount;

    @SerializedName("max_click_count")
    private int maxClickCount;

    @SerializedName("min_click_count")
    private int minClickCount;

    @SerializedName("ave_click_count")
    private int aveClickCount;

    @SerializedName("max_watch_number")
    private int maxWatchCount;

    @SerializedName("min_watch_number")
    private int minWatchCount;

    @SerializedName("ave_watch_number")
    private int aveWatchCount;

    @SerializedName("max_coin")
    private int maxCoin;

    @SerializedName("min_coin")
    private int minCoin;

    @SerializedName("ave_coin")
    private int aveCoin;

    @SerializedName("max_gift_coin")
    private int maxGiftCoin;

    @SerializedName("min_gift_coin")
    private int minGiftCoin;

    @SerializedName("ave_gift_coin")
    private int aveGiftCoin;

    @SerializedName("total_coin")
    private int totalCoin;

    public int getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(int liveCount) {
        this.liveCount = liveCount;
    }

    public int getMaxClickCount() {
        return maxClickCount;
    }

    public void setMaxClickCount(int maxClickCount) {
        this.maxClickCount = maxClickCount;
    }

    public int getMinClickCount() {
        return minClickCount;
    }

    public void setMinClickCount(int minClickCount) {
        this.minClickCount = minClickCount;
    }

    public int getAveClickCount() {
        return aveClickCount;
    }

    public void setAveClickCount(int aveClickCount) {
        this.aveClickCount = aveClickCount;
    }

    public int getMaxWatchCount() {
        return maxWatchCount;
    }

    public void setMaxWatchCount(int maxWatchCount) {
        this.maxWatchCount = maxWatchCount;
    }

    public int getMinWatchCount() {
        return minWatchCount;
    }

    public void setMinWatchCount(int minWatchCount) {
        this.minWatchCount = minWatchCount;
    }

    public int getAveWatchCount() {
        return aveWatchCount;
    }

    public void setAveWatchCount(int aveWatchCount) {
        this.aveWatchCount = aveWatchCount;
    }

    public int getMaxCoin() {
        return maxCoin;
    }

    public void setMaxCoin(int maxCoin) {
        this.maxCoin = maxCoin;
    }

    public int getMinCoin() {
        return minCoin;
    }

    public void setMinCoin(int minCoin) {
        this.minCoin = minCoin;
    }

    public int getAveCoin() {
        return aveCoin;
    }

    public void setAveCoin(int aveCoin) {
        this.aveCoin = aveCoin;
    }

    public int getMaxGiftCoin() {
        return maxGiftCoin;
    }

    public void setMaxGiftCoin(int maxGiftCoin) {
        this.maxGiftCoin = maxGiftCoin;
    }

    public int getMinGiftCoin() {
        return minGiftCoin;
    }

    public void setMinGiftCoin(int minGiftCoin) {
        this.minGiftCoin = minGiftCoin;
    }

    public int getAveGiftCoin() {
        return aveGiftCoin;
    }

    public void setAveGiftCoin(int aveGiftCoin) {
        this.aveGiftCoin = aveGiftCoin;
    }

    public int getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(int totalCoin) {
        this.totalCoin = totalCoin;
    }
}
