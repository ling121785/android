package toolbox.ll.com.toolbox.ui.user;

import android.accounts.Account;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.InComeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshBase;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshRecyclerView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseTitleActivity;

public class MyFansActivity extends BaseTitleActivity implements  PullToRefreshBase.OnRefreshListener2<RecyclerView>{
    @BindView(R.id.fans_lv_list)
    PullToRefreshRecyclerView mRViewList;

    MyFansAdapter mAdapter;

    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        super.initView();
        addContent(R.layout.activity_my_fans);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        setTitle("我的收益");
        mRViewList.setMode(PullToRefreshBase.Mode.BOTH);
        mRViewList.setOnRefreshListener(this);
        List<AccountBean> mlist=new ArrayList<AccountBean>();
        ;        mAdapter=new MyFansAdapter(this,mlist);
        for(int i=0;i<20;i++){
            mlist.add(new AccountBean());
        }
        mRViewList.getRefreshableView().setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.border));
        mRViewList.getRefreshableView().addItemDecoration(divider);
        mRViewList.getRefreshableView().setAdapter(mAdapter);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> RecyclerView) {
        ToastUtils.showToast(this,"下拉刷新");
        mRViewList.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> RecyclerView) {
        ToastUtils.showToast(this,"加载更多");
        mRViewList.onRefreshComplete();

    }
}
