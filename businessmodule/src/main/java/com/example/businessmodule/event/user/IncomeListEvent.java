package com.example.businessmodule.event.user;

import com.example.businessmodule.bean.InComeBean;
import com.example.businessmodule.bean.LiveBean;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.rest.BaseListResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ll on 2018/4/16.
 */

public class IncomeListEvent extends BaseEvent<IncomeListEvent.Request,BaseListResponse<InComeBean>> {
    public IncomeListEvent(long eventId, int page,long startTime,long endTime) {
        super(eventId);
        setRequest(new Request(page,startTime,endTime));
    }

    public class Request{
        private int page;
        private long startTime;
        private long endTime;
        private int type=0;//0:按天,1:按月，2:按年

        public Request(int page,long startTime,long endTime) {
            this.page = page;
            this.startTime=startTime;
            this.endTime=endTime;
        }

        public int getPage() {
            return page;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public interface Rest {
        @GET("live/statistics/time")
        Observable<BaseListResponse<InComeBean>> request(@Query("access_token") String token,@Query("type") int type, @Query("page") int page,@Query("start_time") Long startTime, @Query("end_time") long endTime);
    }
}
