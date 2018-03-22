package com.example.businessmodule.utils;

/**
 * Created by root on 2016/6/20.
 */
public class ResultCode {
    //本地错误码：
    public static final String GENERAL_ERROR_NONE = "";//未知错误
    public static final String GENERAL_ERROR_OUT_TIME = "time out";//超时


    //merchandise list
    public static final int GENERAL_ERROR_NO_COMMNUNITY_CODE = 0;
    // server error code
    public static final int AUTH_FAIL = 1;
    public static final int ERROR_NOT_PERMISSION = 2;


    // local error code
    public static final int SUCCESS = 0;
    public static final int NONE = -1;
    public static final int UNKNOW_ERROR = -2;//未知错误
    public static final int TIME_OUT = -3;//超时
    public static final int ANALYSIS_ERROR = -4;//解析出错
    public static final int PROCESS_ERROR = -5;
    public static final int FILE_UPLOAD_ERROR = -6;
    public static final int GENERAL_NO_NETWORK = -8;//无网络
    public static final int GENERAL_CONNECT_SERVER = -9;//无法连接服务器
    public static final int GENERAL_CONNECT_DOWN_ERROR = -10;//下载失败
    public static final int BATABASE_NO_DATA = -11;
    public static final int PARAMETER_ERROR = -12;
    public static final int DOWN_APK_URL_ERROR = -13;//apk下载地址有误


    public static final int NETEASE_ERR=-22;
}
