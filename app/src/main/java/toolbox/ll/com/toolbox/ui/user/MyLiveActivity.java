package toolbox.ll.com.toolbox.ui.user;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.businessmodule.bean.InComeBean;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.event.user.IncomeListEvent;
import com.example.businessmodule.event.user.LiveRecordListEvent;
import com.example.businessmodule.utils.EventId;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshBase;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshRecyclerView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseTitleActivity;

public class MyLiveActivity extends BaseTitleActivity implements  PullToRefreshBase.OnRefreshListener2<RecyclerView>{
    @BindView(R.id.income_tv_time)
    TextView mTVTime;

    @BindView(R.id.income_lv_list)
    PullToRefreshRecyclerView mRViewList;

    MyLiveAdapter mAdapter;

    private int page=0;


    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        super.initView();
        addContent(R.layout.activity_my_live);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        setTitle("我的直播");
        mRViewList.setMode(PullToRefreshBase.Mode.BOTH);
        mRViewList.setOnRefreshListener(this);
       mAdapter=new MyLiveAdapter(this,null);

        mRViewList.getRefreshableView().setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.border));
        mRViewList.getRefreshableView().addItemDecoration(divider);
        mRViewList.getRefreshableView().setAdapter(mAdapter);
        request(0);
    }

    @OnClick(R.id.income_ib_timeFiler)
    public void timeFilerClick(){
        ToastUtils.showToast(this,"点击");
    }

    private void request(int page){
        BusinessInterface.getInstance().request(new LiveRecordListEvent(EventId.MY_LIVE_LIST, page));
    }

    @Subscribe
    public void liveRecordListEvent(LiveRecordListEvent event){
        mRViewList.onRefreshComplete();
        page=event.request().getPage();
        if(event.isSuccess()){
            if(event.request().getPage()==0){
                mAdapter.setDatas(event.response().getDataList());
                mAdapter.notifyDataSetChanged();
            }else{
                mAdapter.addData(event.response().getDataList());
            }
            if(mAdapter.getItemCount()>=event.response().getTotal()){
                mRViewList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            }else{
                mRViewList.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }

    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> RecyclerView) {
        request(0);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> RecyclerView) {
        request(page+1);

    }
}
