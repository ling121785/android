package com.example.businessmodule.rest.interceptor;


import android.text.TextUtils;

import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.simpleEvent.AuthFailEvent;
import com.example.businessmodule.exception.UrlEncodeException;
import com.example.businessmodule.rest.RestApiMethod;
import com.example.businessmodule.rest.RestApiUrl;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import toolbox.ll.com.common.BuildConfig;

/**
 * Rest拦截器 <br/>
 * <p/>
 * 1. url添加参数；
 * 2. 系统返回的错误数据处理；
 */
public class RestInterceptor implements Interceptor {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String KEY_TOKEN = "authorization";
    public static final String KEY_APP_NAME = "appname";
    public static final String KEY_APP_ACCEPT = "accept";

    public static final String APP_NAME = "resident_app";
    private static final String TOKEN_FORMAT = "@token";
    private static final String ACCEPT = "application/json; version=1";
    private static final String TOKEN = "Bearer " + TOKEN_FORMAT;

    @Override
    public Response intercept(Chain chain) throws IOException {
        String logMsg = "";
        try {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .url(RestApiUrl.encodeParameter(request.url()))
                    .addHeader(KEY_APP_NAME, APP_NAME)
                    .addHeader(KEY_APP_ACCEPT, ACCEPT);
            if (RestApiUrl.hasToken(request.url().toString())) {
                if (TextUtils.isEmpty(BusinessSession.getInstance().getToken())) {
                    throw new IOException(AuthFailEvent.TOKEN_IS_NULL);
                }
                builder.addHeader(KEY_TOKEN, TOKEN.replace(TOKEN_FORMAT, BusinessSession.getInstance().getToken()));
            }

            request = builder.build();

            logMsg += "method : " + request.method() + "\n";
            logMsg += "url : " + request.url() + "\n";
            if (RestApiMethod.POST.getName().equals(request.method()))
                logMsg += "request : " + bodyToString(request) + "\n";
            Response response = chain.proceed(request);
//            if (!isGzip(response) && !request.url().toString().contains(RestApiUrl.URL_PIC_VERIFY)
//                    &&(request.url().toString().contains(RestApiUrl.BASE_URL))) {
//                String responseJson = bodyToString(response);
//                if (responseJson.contains(ResultCode.TOKEN_PASE_DUE)
//                        || responseJson.contains(ResultCode.TOKEN_FAILD)
//                        || responseJson.contains(ResultCode.TOKEN_VERIFY)) {
//                    BusinessBus.getInstance().postResponse(new AuthFailEvent(AuthFailEvent.TOKEN_IS_NULL));
//                }
//                ResponseBody responseBody = ResponseBody.create(JSON, responseJson);
//                response = response.newBuilder().body(responseBody).build();
//            }

            return response;
        } catch (IOException e) {
            logMsg += "response IOException e: " + e.getMessage();
            throw e;
        } catch (UrlEncodeException e) {
            logMsg += "encode url error : " + e.getMessage();
            BusinessInterface.getInstance().postResponse(new AuthFailEvent(e.getMessage()));
            throw new IOException(e.getMessage());
        } finally {
//            Logger.t(BuildConfig.ModuleName).i(logMsg);
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
