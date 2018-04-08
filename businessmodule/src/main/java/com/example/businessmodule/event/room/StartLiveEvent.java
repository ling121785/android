package com.example.businessmodule.event.room;

import com.example.businessmodule.event.BaseEvent;

import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class StartLiveEvent extends BaseEvent<StartLiveEvent.Request,String> {
    public StartLiveEvent(long eventId, String liveId) {
        super(eventId);
        setRequest(new Request(liveId));
    }
    public  class Request{
        String liveId;

        public Request(String liveId) {
            this.liveId = liveId;
        }

        public String getLiveId() {
            return liveId;
        }
    }


    public interface Rest {
        @POST
        Observable<String> request(@Url String url, @Query("access_token") String token);
    }
}
