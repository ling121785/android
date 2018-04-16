package toolbox.ll.com.toolbox.ui.user;

import android.accounts.Account;
import android.graphics.pdf.PdfDocument;
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
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.event.user.FansListEvent;
import com.example.businessmodule.utils.EventId;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    private int page=0;

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
        setTitle("我的粉丝");
        mRViewList.setMode(PullToRefreshBase.Mode.BOTH);
        mRViewList.setOnRefreshListener(this);
        mAdapter=new MyFansAdapter(this,null);
        mRViewList.getRefreshableView().setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.border));
        mRViewList.getRefreshableView().addItemDecoration(divider);
        mRViewList.getRefreshableView().setAdapter(mAdapter);
        request(page);
    }

    private void request(int page){
        BusinessInterface.getInstance().request(new FansListEvent(EventId.MY_FNAS_LIST, page));
    }

    @Subscribe
    public void fansListEvent(FansListEvent event){
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
