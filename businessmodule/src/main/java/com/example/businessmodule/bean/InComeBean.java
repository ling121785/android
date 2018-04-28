package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 2018/4/6.
 */

public class InComeBean {

    @SerializedName("max_click_count")
    private int maxClickCount;

    @SerializedName("min_click_count")
    private int minClickCount;

    @SerializedName("ave_click_count")
    private int aveClickCount;

    @SerializedName("total_click_count")
    private int totalClickCount;

    @SerializedName("max_watch_number")
    private int maxWatchNumber;

    @SerializedName("min_watch_number")
    private int minWatchNumber;

    @SerializedName("ave_watch_number")
    private int aveWatchNumber;

    @SerializedName("max_coin")
    private int maxCoin;

    @SerializedName("min_coin")
    private int minCoin;

    @SerializedName("ave_coin")
    private int aveCoin;

    @SerializedName("total_coin")
    private int totalCoin;

    @SerializedName("max_gift_coin")
    private int maxGiftCoin;

    @SerializedName(" min_gift_coin")
    private int minGiftCoin;

    @SerializedName("ave_gift_coin")
    private int aceGiftCoin;

    @SerializedName("total_gift_coin")
    private int  totalGiftCoin;

    @SerializedName("time")
    private String time;

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

    public int getTotalClickCount() {
        return totalClickCount;
    }

    public void setTotalClickCount(int totalClickCount) {
        this.totalClickCount = totalClickCount;
    }

    public int getMaxWatchNumber() {
        return maxWatchNumber;
    }

    public void setMaxWatchNumber(int maxWatchNumber) {
        this.maxWatchNumber = maxWatchNumber;
    }

    public int getMinWatchNumber() {
        return minWatchNumber;
    }

    public void setMinWatchNumber(int minWatchNumber) {
        this.minWatchNumber = minWatchNumber;
    }

    public int getAveWatchNumber() {
        return aveWatchNumber;
    }

    public void setAveWatchNumber(int aveWatchNumber) {
        this.aveWatchNumber = aveWatchNumber;
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

    public int getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(int totalCoin) {
        this.totalCoin = totalCoin;
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

    public int getAceGiftCoin() {
        return aceGiftCoin;
    }

    public void setAceGiftCoin(int aceGiftCoin) {
        this.aceGiftCoin = aceGiftCoin;
    }

    public int getTotalGiftCoin() {
        return totalGiftCoin;
    }

    public void setTotalGiftCoin(int totalGiftCoin) {
        this.totalGiftCoin = totalGiftCoin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
