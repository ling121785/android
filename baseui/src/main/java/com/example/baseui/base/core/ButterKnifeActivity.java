package com.example.baseui.base.core;

import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by chenfiona on 16/4/6.
 */
public abstract  class ButterKnifeActivity extends SubscribeActivity implements InitInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.getInstance().pushActivity(this);
        super.onCreate(savedInstanceState);

        beforeInit(savedInstanceState);
        if(getLayoutResID()!=0){
            setContentView(getLayoutResID());
        }
        initView();
        ButterKnife.bind(this);
        afterInit( savedInstanceState);
    }

    public void initView(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
        ButterKnife.unbind(this);
    }
}
