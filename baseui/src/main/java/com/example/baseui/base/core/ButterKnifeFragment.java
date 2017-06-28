package com.example.baseui.base.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by chenfiona on 16/4/6.
 */
public  abstract  class ButterKnifeFragment extends SubscribeFragment implements InitInterface {
    private View mContentView;
    private boolean isFirst = true;//是否第一次显示
    private boolean isReady = false;//是否初始化

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeInit(savedInstanceState);
        mContentView = inflater.inflate(getLayoutResID(), null);
        initView(inflater);
        ButterKnife.bind(this, mContentView);
        endInit();
        afterInit(savedInstanceState);
        isReady=true;//设置初始化标示
        //第一次并且用户可见
        if (getUserVisibleHint()&&isFirst){
            lazyLoad();
            isFirst=false;
        }

        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
        }
    }

    private void onVisible(){
        //第一显示
        if(isFirst&&isReady){
            isFirst=false;
            lazyLoad();
        }


    }

    @Override
    public void onDestroy() {
        isReady = false;
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void initView(LayoutInflater inflater) {
    }

    public void endInit(){}

    public View getmContentView() {
        return mContentView;
    }


    public void lazyLoad() {

    }

    public boolean isReady() {
        return isReady;
    }
}
