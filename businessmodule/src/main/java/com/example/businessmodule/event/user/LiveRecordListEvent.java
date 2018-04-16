package com.example.businessmodule.event.user;

import com.example.businessmodule.bean.LiveBean;
import com.example.businessmodule.bean.RoomBean;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.room.CreateRoomEvent;
import com.example.businessmodule.rest.BaseListResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ll on 2018/4/16.
 */

public class LiveRecordListEvent extends BaseEvent<LiveRecordListEvent.Request,BaseListResponse<LiveBean>> {
    public LiveRecordListEvent(long eventId,int page) {
        super(eventId);
        setRequest(new Request(page));
    }

    public class Request{
        private String selectType;
        private int page;
        private int size;
        private int liveType;
        private boolean isCharge;
        private boolean isPrivate;

        public Request(int page) {
            this.page = page;
        }

        public String getSelectType() {
            return selectType;
        }

        public int getPage() {
            return page;
        }

        public int getSize() {
            return size;
        }

        public int getLiveType() {
            return liveType;
        }

        public boolean isCharge() {
            return isCharge;
        }

        public boolean isPrivate() {
            return isPrivate;
        }
    }

    public interface Rest {
        @GET("live")
        Observable<BaseListResponse<LiveBean>> request(@Query("access_token") String token, @Query("page") int page);
    }
}
