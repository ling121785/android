package toolbox.ll.com.toolbox.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenfiona on 16/4/6.
 */
public  abstract  class ButterKnifeFragment extends SubscribeFragment implements BaseInit {
    private View mContentView;
    private boolean isFirst = true;//是否第一次显示
    private boolean isReady = false;//是否初始化
    private Unbinder mUnbinder=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeInit(savedInstanceState);
        mContentView = inflater.inflate(getLayoutResId(), null);
        initView(inflater);
        mUnbinder=ButterKnife.bind(this, mContentView);
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
        if(mUnbinder!=null)
        mUnbinder.unbind();
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
