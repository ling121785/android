package com.example.baseui.base.core;

import android.os.Bundle;

/**
 * Created by root on 2016/5/23.
 */
public interface InitInterface {
    /**
     * setContentView 之前
     * @param savedInstanceState
     */
    void beforeInit(Bundle savedInstanceState);
    int getLayoutResID();
    /**
     * setContentView， ButterKnife.bind(this)之后
     * @param savedInstanceState
     */
    void afterInit(Bundle savedInstanceState);

}
