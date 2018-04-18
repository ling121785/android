package toolbox.ll.com.toolbox.ui.user;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.businessmodule.bean.FansContriButionsBean;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.event.user.FansContributionListEvent;
import com.example.businessmodule.event.user.IncomeListEvent;
import com.example.businessmodule.utils.EventId;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshBase;
import toolbox.ll.com.pulltorefresh.pulltorefresh.PullToRefreshRecyclerView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseFragment;


public class MyFanContributionsFragment extends BaseFragment implements  PullToRefreshBase.OnRefreshListener2<RecyclerView>{

    @BindView(R.id.recyclerView)
    PullToRefreshRecyclerView mRViewList;

    MyFanContributionsAdapter mAdapter;

    private int page=0;
    private long startTime;
    private long endTime;
    private long eventId;

    public MyFanContributionsFragment setTime(long eventId,long startTime,long endTime){
        this.startTime=startTime;
        this.endTime=endTime;
        this.eventId=eventId;
        return this;
    }


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
        mRViewList.setMode(PullToRefreshBase.Mode.BOTH);
        mRViewList.setOnRefreshListener(this);
        mRViewList.getRefreshableView().setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter=new MyFanContributionsAdapter(getContext(),null);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.border));
        mRViewList.getRefreshableView().addItemDecoration(divider);
        mRViewList.getRefreshableView().setAdapter(mAdapter);
        request(0);
    }

    private void request(int page){
        BusinessInterface.getInstance().request(new FansContributionListEvent(eventId, page,startTime/1000,endTime/1000));
    }

    @Subscribe
    public void fansContributionListEvent(FansContributionListEvent event){
        if(event.getEventId()!=eventId)
            return;
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
