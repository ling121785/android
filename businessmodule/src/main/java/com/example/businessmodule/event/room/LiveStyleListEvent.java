package com.example.businessmodule.event.room;

import com.example.businessmodule.bean.GiftBean;
import com.example.businessmodule.bean.LiveStyleBean;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.rest.BaseListResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class LiveStyleListEvent extends BaseEvent<LiveStyleListEvent.Request,BaseListResponse<LiveStyleBean>> {
    public LiveStyleListEvent(long eventId) {
        super(eventId);

    }
    public  class Request{
    }


    public interface Rest {
        @GET("live/style")
        Observable<BaseListResponse<LiveStyleBean>> request(@Query("access_token") String token);
    }
}
