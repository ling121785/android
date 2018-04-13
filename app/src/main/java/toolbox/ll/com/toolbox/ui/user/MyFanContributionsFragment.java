package toolbox.ll.com.toolbox.ui.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseFragment;


public class MyFanContributionsFragment extends BaseFragment{

    @BindView(R.id.recyclerView)
    RecyclerView mRView;
    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_fan_contributions;
    }

    @Override
    public void initView() {

    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}
