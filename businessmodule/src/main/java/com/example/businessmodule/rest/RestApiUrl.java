package com.example.businessmodule.rest;


import android.text.TextUtils;

import com.example.businessmodule.business.BaseBusiness;
import com.example.businessmodule.exception.UrlEncodeException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import toolbox.ll.com.common.utility.StringUtils;

/**
 * Rest client constances
 */
public class RestApiUrl {

    public static final int DEFAULT_LIST_SIZE =50;
    public static final int DEFAULT_LIST_ONE_SIZE =1;
    public static final int DEFAULT_LIST_CURSOR = 0;
    public static final int DEFAULT_LIST_START_TIME = -1;
    public static final int DEFAULT_LIST_END_TIME = -1;

    public static final String BASE_URL_TEST="https://api.cqhodc.com/api/";
    public static  String BASE_URL=BASE_URL_TEST;
    public static final String PARAM_PAGE_NUM="page";
    public static final String PARAM_PAGE_SIZE="size";
    public static final String PARAM_NUMBER = "number";

    private static List<RestApiUrl> restApiUrls;
    static {
        restApiUrls = new ArrayList<RestApiUrl>();
        // account
//        restApiUrls.add(new RestApiUrl(URL_MCODE, URL_MCODE, false, true, false));



    }

    private static String addParameter(String url, String name, String value) {
        if (url.contains("?"))
            url += "&";
        else
            url += "?";
        return url + name + "=" + value;
    }

    public static HttpUrl encodeParameter(HttpUrl httpUrl) throws UrlEncodeException {
        String url = httpUrl.toString();
        for (RestApiUrl restApiUrl : restApiUrls) {
            String urlKey = BASE_URL + restApiUrl.getKey();
            if (url.startsWith(urlKey)) {
                url = url.replace(urlKey, BASE_URL + restApiUrl.getUrl());
                return HttpUrl.parse(url);
            }
        }
        return HttpUrl.parse(url);
    }


    public static boolean hasToken(String url){
        if(TextUtils.isEmpty(url))
            return false;
        for(RestApiUrl restApiUrl:restApiUrls){
            if(url.contains(restApiUrl.getUrl())){
                return restApiUrl.hasToken();
            }
        }
        return true;
    }

    private String key;
    private String url;
    private boolean hasToken;
    private boolean hasTimestamp;
    private boolean hasSign;

    private RestApiUrl(String key, String url, boolean hasToken, boolean hasTimestamp, boolean hasSign) {
        this.key = key;
        this.url = url;
        this.hasToken = hasToken;
        this.hasTimestamp = hasTimestamp;
        this.hasSign = hasSign;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

    public boolean hasToken() {
        return hasToken;
    }

    public static void resetBaseUrl(String ip){
        if(StringUtils.isEmpty(ip))
            return;
        BASE_URL=ip;
//        BusinessPrefences.getInstance().setServerIP(BASE_URL);
        BaseBusiness.resetBaseUrl();
    }

}
