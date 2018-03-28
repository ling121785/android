package com.example.businessmodule.event.account;

import com.example.businessmodule.event.BaseEvent;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by Administrator on 2018/3/22.
 */

public class LogoutEvent extends BaseEvent<LogoutEvent.Request,String> {

    public class Request{
    }
    public LogoutEvent(long eventId) {
        super(eventId);
    }
}
