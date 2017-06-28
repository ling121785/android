package com.example.baseui.base.core;

import android.os.Bundle;

import com.example.businessmodule.core.BusinessInterface;


/**
 * Otto为了性能，代码意图清晰，@Subscribe，@Produce方法必须定义在直接的作用类上，而不能定义在基类而被继承。
 * Created by chenfiona on 16/4/6.
 */
public class SubscribeActivity extends AnimationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusinessInterface.getInstance().registerResponse(this);
    }

    @Override
    protected void onDestroy() {
        BusinessInterface.getInstance().unregisterResponse(this);
        super.onDestroy();
    }
}
