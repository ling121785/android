package toolbox.ll.com.toolbox.ui.user;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.businessmodule.bean.InComeBean;
import com.example.businessmodule.bean.LiveBean;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.user.IncomeListEvent;
import com.example.businessmodule.event.user.LiveRecordListEvent;
import com.example.businessmodule.event.user.LiveStatisticsEvent;
import com.example.businessmodule.utils.EventId;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.DateUtils;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshBase;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshRecyclerView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseTitleActivity;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;
import toolbox.ll.com.toolbox.utils.DialogUtil;

public class MyLiveActivity extends BaseTitleActivity implements  PullToRefreshBase.OnRefreshListener2<RecyclerView>{
    @BindView(R.id.income_tv_time)
    TextView mTVTime;

    @BindView(R.id.income_lv_list)
    PullToRefreshRecyclerView mRViewList;

    @BindView(R.id.live_tv_count)
    TextView mTVCount;

    @BindView(R.id.live_tv_maxCoin)
    TextView mTVMaxCoin;

    @BindView(R.id.live_tv_minCoin)
    TextView mTVMinCoin;

    @BindView(R.id.live_tv_aveCoin)
    TextView mTVAveCoin;

    @BindView(R.id.live_tv_maxWatch)
    TextView mTVMaxWatch;

    @BindView(R.id.live_tv_minWatch)
    TextView mTVMinWatch;

    @BindView(R.id.live_tv_aveWatch)
    TextView mTVAveWatch;


    MyLiveAdapter mAdapter;

    private int page=0;
    private long startTime;
    private long endTime;


    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        super.initView();
        addContent(R.layout.activity_my_live);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void afterInit(Bundle savedInstanceState) {
        setTitle("我的直播");
        mRViewList.setMode(PullToRefreshBase.Mode.BOTH);
        mRViewList.setOnRefreshListener(this);
       mAdapter=new MyLiveAdapter(this,null,new BaseRListAdapter.OnItemClickListener<LiveBean>(){

           @Override
           public void onItemListener(int positon, LiveBean data) {
               data.setCheck(!data.isCheck());
               mAdapter.notifyDataSetChanged();
           }
       });

        mRViewList.getRefreshableView().setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.border));
        mRViewList.getRefreshableView().addItemDecoration(divider);
        mRViewList.getRefreshableView().setAdapter(mAdapter);
        initFilterFime();
        request(0);
    }

    @SuppressLint("WrongConstant")
    private void initFilterFime(){
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        this.startTime=-1;
        calendar.add(Calendar.DAY_OF_MONTH,8);
        calendar.add(Calendar.SECOND,-1);
        this.endTime=calendar.getTimeInMillis();
        mTVTime.setText("截止至"+DateUtils.cutYearAndMonthAndDay(endTime));
    }

    @OnClick(R.id.income_ib_timeFiler)
    public void timeFilerClick(){
        DialogUtil.chooseTimeDialog(this, startTime, endTime, new DialogUtil.DialogClickListener() {
            @Override
            public void comfirm(Object... obj) {
                startTime=(long)obj[0];
                endTime=(long)obj[1];
                if(startTime==-1){
                    mTVTime.setText("截止至"+DateUtils.cutYearAndMonthAndDay(endTime));
                }else{
                    mTVTime.setText(DateUtils.cutYearAndMonthAndDay(startTime)+"至"+DateUtils.cutYearAndMonthAndDay(endTime));
                }
                request(0);
            }

            @Override
            public void cancel() {

            }
        }).show();
    }

    private void request(int page){
        if(page==0){
            BusinessInterface.getInstance().request(new LiveStatisticsEvent(EventId.MY_LIVE_LIST, 0,startTime/1000,endTime/1000));
        }
        BusinessInterface.getInstance().request(new LiveRecordListEvent(EventId.MY_LIVE_LIST, page,startTime/1000,endTime/1000));
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

    @Subscribe
    public void liveStatisticsEvent(LiveStatisticsEvent event){
        if(event.isSuccess()){
            mTVCount.setText(event.response().getLiveCount()+"");
            mTVMaxCoin.setText(event.response().getMaxGiftCoin()+"");
            mTVMinCoin.setText(event.response().getMinGiftCoin()+"");
            mTVAveCoin.setText(event.response().getAveGiftCoin()+"");
            mTVMaxWatch.setText(event.response().getMaxWatchCount()+"");
            mTVMinWatch.setText(event.response().getMinWatchCount()+"");
            mTVAveWatch.setText(event.response().getAveWatchCount()+"");
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
