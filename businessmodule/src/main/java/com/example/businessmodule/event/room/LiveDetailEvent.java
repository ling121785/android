package com.example.businessmodule.event.room;

import com.example.businessmodule.R;
import com.example.businessmodule.bean.LiveInfoBean;
import com.example.businessmodule.event.BaseEvent;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;
import toolbox.ll.com.common.widget.CircleImageView;

/**
 * Created by Administrator on 2018/3/22.
 */

public class LiveDetailEvent extends BaseEvent<LiveDetailEvent.Request,LiveInfoBean> {

    public LiveDetailEvent(long eventId, String liveId) {
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
        @GET
        Observable<LiveInfoBean> request(@Url String url, @Query("access_token") String token);
    }
}
