package toolbox.ll.com.toolbox.ui.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.businessmodule.bean.FanContriButionsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseFragment;


public class MyFanContributionsFragment extends BaseFragment{

    @BindView(R.id.recyclerView)
    RecyclerView mRView;

    MyFanContributionsAdapter mAdapter;
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
        List<FanContriButionsBean> data=new ArrayList<>();
        data.add(new FanContriButionsBean());
        data.add(new FanContriButionsBean());
        data.add(new FanContriButionsBean());
        data.add(new FanContriButionsBean());
        mAdapter=new MyFanContributionsAdapter(getContext(),data);
        mRView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRView.setAdapter(mAdapter);
    }
}
