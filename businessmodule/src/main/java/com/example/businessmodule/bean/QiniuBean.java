package com.example.businessmodule.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/21.
 */

public class QiniuBean {
    @SerializedName("token")
    private String token;

    @SerializedName("url")
    private String url;

    @SerializedName("upload_url")
    private String upload_url;

    @SerializedName("key")
    private String key;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpload_url() {
        return upload_url;
    }

    public void setUpload_url(String upload_url) {
        this.upload_url = upload_url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
