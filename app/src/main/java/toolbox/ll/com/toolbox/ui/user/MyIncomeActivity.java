package toolbox.ll.com.toolbox.ui.user;

import android.annotation.SuppressLint;
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
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.event.user.FansListEvent;
import com.example.businessmodule.event.user.IncomeListEvent;
import com.example.businessmodule.utils.EventId;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.DateUtils;
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

    private int page=0;
    private long startTime;
    private long endTime;


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
;        mAdapter=new MyIncomeAdapter(this,null);
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
        this.startTime=calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH,8);
        calendar.add(Calendar.SECOND,-1);
        this.endTime=calendar.getTimeInMillis();
        mTVTime.setText(DateUtils.cutYearAndMonthAndDay(startTime)+"至"+DateUtils.cutYearAndMonthAndDay(endTime));
    }

    @OnClick(R.id.income_ib_timeFiler)
    public void timeFilerClick(){

        DialogUtil.chooseTimeDialog(this,startTime,endTime, new DialogUtil.DialogClickListener() {
            @Override
            public void comfirm(Object... obj) {
                startTime=(long)obj[0];
                endTime=(long)obj[1];
                mTVTime.setText(DateUtils.cutYearAndMonthAndDay(startTime)+"至"+DateUtils.cutYearAndMonthAndDay(endTime));
                request(0);
            }

            @Override
            public void cancel() {

            }
        }).show();
    }

    private void request(int page){
        BusinessInterface.getInstance().request(new IncomeListEvent(EventId.MY_INCOME_LIST, page,startTime/1000,endTime/1000));
    }

    @Subscribe
    public void incomeListEvent(IncomeListEvent event){
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
