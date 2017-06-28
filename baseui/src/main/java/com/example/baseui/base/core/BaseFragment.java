package com.example.baseui.base.core;

/**
 * Created by root on 2016/6/14.
 */
public abstract class BaseFragment<T> extends ButterKnifeFragment {

    private T mData;

    protected void setData(T data) {
        this.mData = data;
    }

    protected void refresh() {
    }

    public T getData() {
        return mData;
    }

    public void updateDatas(T mData) {
        setData(mData);
        refresh();
    }


}
