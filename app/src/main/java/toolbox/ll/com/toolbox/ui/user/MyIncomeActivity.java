package toolbox.ll.com.toolbox.ui.user;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.businessmodule.bean.InComeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshBase;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshListView;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshRecyclerView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;
import toolbox.ll.com.toolbox.ui.base.BaseTitleActivity;
import toolbox.ll.com.toolbox.utils.DialogUtil;

public class MyIncomeActivity extends BaseTitleActivity implements  PullToRefreshBase.OnRefreshListener2<RecyclerView>{
    @BindView(R.id.income_tv_time)
    TextView mTVTime;

    @BindView(R.id.income_lv_list)
    PullToRefreshRecyclerView mRViewList;

    MyIncomeAdapter mAdapter;


    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        super.initView();
        addContent(R.layout.activity_my_income);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        setTitle("我的收益");
        mRViewList.setMode(PullToRefreshBase.Mode.BOTH);
        mRViewList.setOnRefreshListener(this);
        List<InComeBean> mlist=new ArrayList<InComeBean>();
;        mAdapter=new MyIncomeAdapter(this,mlist);
        for(int i=0;i<5;i++){
            mlist.add(new InComeBean());
        }
        mRViewList.getRefreshableView().setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.border));
        mRViewList.getRefreshableView().addItemDecoration(divider);
        mRViewList.getRefreshableView().setAdapter(mAdapter);
    }

    @OnClick(R.id.income_ib_timeFiler)
    public void timeFilerClick(){

        DialogUtil.chooseTimeDialog(this, new DialogUtil.DialogClickListener() {
            @Override
            public void comfirm(Object... obj) {
                com.orhanobut.logger.Logger.i("startTime",obj[0]);
                com.orhanobut.logger.Logger.i("startTime",obj[1]);
            }

            @Override
            public void cancel() {

            }
        }).show();
//        List<InComeBean> mlist=mAdapter.getmDatas();
//        for(int i=0;i<10;i++){
//            mlist.add(new InComeBean());
//        }
//        mAdapter.setDatas(mlist);
//        mAdapter.notifyDataSetChanged();

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
