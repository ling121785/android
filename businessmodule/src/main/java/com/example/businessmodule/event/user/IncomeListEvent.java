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
    public IncomeListEvent(long eventId, int page) {
        super(eventId);
        setRequest(new Request(page));
    }

    public class Request{
        private int page;

        public Request(int page) {
            this.page = page;
        }

        public int getPage() {
            return page;
        }
    }

    public interface Rest {
        @GET("account/income")
        Observable<BaseListResponse<InComeBean>> request(@Query("access_token") String token, @Query("page") int page);
    }
}
