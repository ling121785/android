package com.example.businessmodule.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 列表响应基类
 */
public class BaseListResponse<T>{
    @SerializedName("total")
    private int total;
    @SerializedName("count")
    private int count;
    @SerializedName("list")
    private List<T> dataList;

    public BaseListResponse() {
    }

    public BaseListResponse(int total, int count, List<T> dataList) {
        this.total = total;
        this.count = count;
        this.dataList = dataList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}


