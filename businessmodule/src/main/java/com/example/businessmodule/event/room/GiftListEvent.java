package com.example.businessmodule.event.room;

import com.example.businessmodule.bean.GiftBean;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.rest.BaseListResponse;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class GiftListEvent extends BaseEvent<GiftListEvent.Request,BaseListResponse<GiftBean>> {
    public GiftListEvent(long eventId, String roomId) {
        super(eventId);
        setRequest(new Request(roomId));
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
        @GET("gift")
        Observable<BaseListResponse<GiftBean>> request(@Query("access_token") String token,@Query("room_id") String roomId);
    }
}
