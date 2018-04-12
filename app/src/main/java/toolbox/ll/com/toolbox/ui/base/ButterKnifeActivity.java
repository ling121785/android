package toolbox.ll.com.toolbox.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/21.
 */

public  abstract class ButterKnifeActivity extends SubscribeActivity implements BaseInit {
    private Unbinder mUnbinder=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit(savedInstanceState);
        setContentView(getLayoutResId());
        initView();
        mUnbinder= ButterKnife.bind(this);
        afterInit(savedInstanceState);
    }
    @Override
    public void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUnbinder!=null)
            mUnbinder.unbind();
    }
}
