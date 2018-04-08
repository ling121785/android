package com.example.businessmodule.rest.interceptor;


import android.text.TextUtils;

import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.simpleEvent.AuthFailEvent;
import com.example.businessmodule.exception.ResponseException;
import com.example.businessmodule.exception.UrlEncodeException;
import com.example.businessmodule.rest.HttpBaseResult;
import com.example.businessmodule.rest.RestApiMethod;
import com.example.businessmodule.rest.RestApiUrl;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import toolbox.ll.com.common.BuildConfig;
import toolbox.ll.com.common.utility.JsonUtils;

/**
 * Rest拦截器 <br/>
 * <p/>
 * 1. url添加参数；
 * 2. 系统返回的错误数据处理；
 */
public class RestInterceptor implements Interceptor {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String KEY_TOKEN = "authorization";
    public static final String KEY_CONTENT_TYPE="Content-Type";

    public static final String VALUE_CONTENT_TYPE="application/json;charset=UTF-8";
    private static final String TOKEN_FORMAT = "@token";
    private static final String TOKEN = "Bearer " + TOKEN_FORMAT;

    @Override
    public Response intercept(Chain chain) throws IOException {
        String logMsg = "";
        try {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .url(RestApiUrl.encodeParameter(request.url()))
                    .addHeader(KEY_CONTENT_TYPE, VALUE_CONTENT_TYPE);
            request = builder.build();
            logMsg += "method : " + request.method() + "\n";
            logMsg += "url : " + request.url() + "\n";
            if (RestApiMethod.POST.getName().equals(request.method()))
                logMsg += "request : " + bodyToString(request) + "\n";
            Response response = chain.proceed(request);
            if (!isGzip(response)) {
                String responseJson = bodyToString(response);
                logMsg += "response : " + responseJson + "\n";
                HttpBaseResult result=JsonUtils.jsonToObj(responseJson, HttpBaseResult.class);
                if(result!=null&&result.getMessage()!=null){
                    throw new IOException(result.getMessage());
                }
                ResponseBody responseBody = ResponseBody.create(JSON, responseJson);
                response = response.newBuilder().body(responseBody).build();
            }

            return response;
        } catch (IOException e) {
            logMsg += "response IOException e: " + e.getMessage();
            throw e;
        } catch (UrlEncodeException e) {
            logMsg += "encode url error : " + e.getMessage();
            BusinessInterface.getInstance().postResponse(new AuthFailEvent(e.getMessage()));
            throw new IOException(e.getMessage());
        }  finally {
            Logger.i(logMsg);
        }
    }

    private String bodyToString(Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }



    private boolean isGzip(Response response) {
        String contentEncode = response.header("Content-Encoding");
        return contentEncode != null && contentEncode.contains("gzip");
    }

    private String bodyToString(Response response) {
        try {
            String json = new String(response.body().bytes());
            response.body().close();
            return json;
        } catch (IOException e) {
            return "did not work";
        }
    }
}
