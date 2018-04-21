package com.example.businessmodule.event.common;

import com.example.businessmodule.bean.QiniuBean;
import com.example.businessmodule.business.BaseBusiness;
import com.example.businessmodule.event.BaseEvent;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/21.
 */

public class QiniuInfoEvent extends BaseEvent<QiniuInfoEvent.Request,QiniuBean> {
    public QiniuInfoEvent(long eventId) {
        super(eventId);
    }
    public  class Request{
        String roomId;

        public Request(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomId() {
            return roomId;
        }
    }


    public interface Rest {
        @GET("storage/info")
        Observable<QiniuBean> request(@Query("access_token") String token);
    }
}
