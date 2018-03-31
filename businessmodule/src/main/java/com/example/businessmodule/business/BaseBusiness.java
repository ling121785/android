package com.example.businessmodule.business;


import android.content.Context;

import com.example.businessmodule.core.BusinessBus;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.simpleEvent.AuthFailEvent;
import com.example.businessmodule.rest.BaseResult;
import com.example.businessmodule.rest.ErrorResponse;
import com.example.businessmodule.rest.RestApiUrl;
import com.example.businessmodule.rest.interceptor.RestInterceptor;
import com.example.businessmodule.utils.ResultCode;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import toolbox.ll.com.common.BuildConfig;
import toolbox.ll.com.common.utility.JsonUtils;


/**
 * 业务基类 <br/>
 * 具体业务执行（如账号/报修/报警等）
 */
public abstract class BaseBusiness {

    private static Retrofit retrofit = null;

    // 线程池中执行任务
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private BaseEvent event;
    private BusinessCallback callback;

    public BaseBusiness(BaseEvent event) {
        this.event = event;
    }

    /**
     * 任务回调设置，任务执行情况监听
     *
     * @param callback 回调
     */
    public void setCallback(BusinessCallback callback) {
        this.callback = callback;
    }

    /**
     * 开始执行任务
     */
    public void execute() {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
    }

    protected abstract void process();

    /**
     * 中断任务
     */
    public abstract void abort();

    /**
     * REST Client retrofit
     *
     * @return retrofit
     */
    protected Retrofit getRetrofit() {
        if (retrofit == null) {
            // 自定义 okhttp client，设置超时、拦截器、缓存等。
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.connectTimeout(5, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(true);
            builder.addNetworkInterceptor(new RestInterceptor());
            builder.sslSocketFactory(getSSL().getSocketFactory());
            builder.hostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // TODO Auto-generated method stub
                    return true;

                }
            }).build();
//            builder.sslSocketFactory(getSSLSocketFactory(BusinessInterface.getInstance().getContext(), "cert.pem"));//添加https
            OkHttpClient client = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(RestApiUrl.BASE_URL)
                    .client(client)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody,Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }
    private SSLContext getSSL(){
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    //实现https的类
    private static SSLSocketFactory getSSLSocketFactory(Context context, String name) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        InputStream inputStream = null;
        Certificate certificate;

        try {
            inputStream = context.getResources().getAssets().open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            certificateFactory = CertificateFactory.getInstance("X.509");
            certificate = certificateFactory.generateCertificate(inputStream);

            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry(name, certificate);

            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {

        }
        return null;
    }


    public static void resetBaseUrl() {
        retrofit = null;
    }


    /**
     * 任务执行完成，调用此函数，响应任务请求者
     */
    protected void responseSuccess() {
        event.setResult(ResultCode.SUCCESS);
        if (callback != null) {
            callback.response(event);
        }
    }

    /**
     * 任务执行完成，调用此函数，响应任务请求者
     *
     * @param e Throwable
     */
    protected void responseError(Throwable e) {
        ErrorResponse errorResponse = null;
        try {
            errorResponse = JsonUtils.jsonToObj(e.getMessage(), ErrorResponse.class);
        } catch (Exception jsonE) {
//            Logger.t(BuildConfig.ModuleName).e("response error [" + e.getMessage() + "][" + jsonE.getMessage() + "]");
        }
        if (errorResponse != null && errorResponse.getErrorCode() != null) {
            event.setResult(Integer.parseInt(errorResponse.getErrorCode()), errorResponse.getErrorMsg());
        } else {
            event.setResult(ResultCode.UNKNOW_ERROR, e.getMessage());
        }

        if (callback != null) {
            callback.response(event);
        }
    }

    /**
     * 任务执行完成，调用此函数，响应任务请求者
     *
     * @param code    错误代码
     * @param message 错误信息
     */
    protected void responseError(int code, String message) {
        event.setResult(code, message);
        if (callback != null) {
            callback.response(event);
        }
    }

    /**
     * 开始执行 REST Client
     * 适用于业务只有一个请求的情况
     *
     * @param observable rxjava
     * @param callback   回调
     * @return Subscription
     */
    protected <K> Subscription startRest(Observable<K> observable, final RestCallback<K> callback) {
        try {
            observable.subscribeOn(Schedulers.io());
            Subscription subscription = observable.subscribe(new Subscriber<K>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    try {
                        if (callback != null)
                            callback.onError();
                        Logger.e(e.getMessage(),event);
                        if (e instanceof UnknownHostException) {
                            responseError(ResultCode.GENERAL_NO_NETWORK, "");
                        } else if (e instanceof ConnectException) {
                            responseError(ResultCode.GENERAL_CONNECT_SERVER, "");

                        } else if(e instanceof JsonSyntaxException){
                            responseError(ResultCode.ANALYSIS_ERROR, "");
                        }
                        else
                            responseError(e);
                    } catch (Exception ex) {
                        Logger.e(ex.getMessage(),event);
                        responseError(ResultCode.UNKNOW_ERROR, ex.getMessage());
                    }

                }

                @Override
                public void onNext(K o) {
                    try {
                        if (callback != null && !callback.onResponse(o)) {
                            responseError(ResultCode.PROCESS_ERROR, "response process error");
                        } else {
                            event.setResponse(o);
                            responseSuccess();
                        }
                    } catch (Exception e) {
                        responseError(ResultCode.PROCESS_ERROR, e.getMessage());
                    }
                }
            });

            return subscription;
        } catch (Exception e) {
            //防止retrofit 内部异常导致崩溃
            responseError(e);
            Logger.i(e.getMessage());
            return null;
        }

    }


    /**
     * 获取当前任务的事件
     *
     * @return event
     */
    public BaseEvent getEvent() {
        return event;
    }

    /**
     * Rest Client 返回数据回调
     *
     * @param <K> rest response
     */
    protected interface RestCallback<K> {
        boolean onResponse(K response);

        boolean onError();
    }

    /**
     * 任务执行情况回调
     */
    public class BusinessCallback {
        public void response(BaseEvent event) {
        }
    }
}
