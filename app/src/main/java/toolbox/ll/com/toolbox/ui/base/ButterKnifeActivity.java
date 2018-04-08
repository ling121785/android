package toolbox.ll.com.toolbox.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/21.
 */

public  abstract class ButterKnifeActivity extends SubscribeActivity implements BaseInit {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit(savedInstanceState);
        setContentView(getLayoutResId());
        initView();
        ButterKnife.bind(this);
        afterInit(savedInstanceState);
    }
    @Override
    public void initView() {

    }
}
