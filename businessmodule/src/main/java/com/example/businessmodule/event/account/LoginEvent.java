package com.example.businessmodule.event.account;

import com.example.businessmodule.event.BaseEvent;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by Administrator on 2018/3/22.
 */

public class LoginEvent extends BaseEvent<LoginEvent.Request,LoginInfo> {

    public class Request{
        private  String account;
        private String password;

        public Request(String account, String password) {
            this.account = account;
            this.password = password;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    public LoginEvent(long eventId,String account,String password) {
        super(eventId);
        setRequest(new Request(account,password));
    }
}
