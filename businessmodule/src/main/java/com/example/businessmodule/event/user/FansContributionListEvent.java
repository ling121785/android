package com.example.businessmodule.event.user;

import com.example.businessmodule.bean.FansContriButionsBean;
import com.example.businessmodule.bean.LiveBean;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.rest.BaseListResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ll on 2018/4/16.
 */

public class FansContributionListEvent extends BaseEvent<FansContributionListEvent.Request,BaseListResponse<FansContriButionsBean>> {
    public FansContributionListEvent(long eventId, int page, long startTime, long endTime) {
        super(eventId);
        setRequest(new Request(page,startTime,endTime));
    }

    public class Request{
        private String selectType;
        private int page;
        private int size;
        private int liveType;
        private boolean isCharge;
        private boolean isPrivate;
        private long startTime;
        private long endTime;

        public Request(int page,long startTime,long endTime) {
            this.page = page;
            this.startTime=startTime;
            this.endTime=endTime;
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

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }
    }

    public interface Rest {
        @GET("account/fans/contribution")
        Observable<BaseListResponse<FansContriButionsBean>> request(@Query("access_token") String token, @Query("page") int page, @Query("start_time") long startTime, @Query("end_time") long endTime);
    }
}
