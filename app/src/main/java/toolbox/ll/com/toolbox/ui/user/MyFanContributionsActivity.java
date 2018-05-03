package toolbox.ll.com.toolbox.ui.user;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.businessmodule.utils.EventId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseTitleActivity;
import toolbox.ll.com.toolbox.ui.base.adapters.SimpleFragmentAdapter;

public class MyFanContributionsActivity extends BaseTitleActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTableLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPage;

    private boolean isLive=false;
    private long liveStartTime;

    @Override
    public void beforeInit(Bundle savedInstanceState) {
        super.beforeInit(savedInstanceState);
        isLive=getIntent().getBooleanExtra("isLive",false);
        liveStartTime=getIntent().getLongExtra("liveStartTime",0);

    }

    @Override
    public void initView() {
        super.initView();
        addContent(R.layout.activity_my_fan_contributions);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void afterInit(Bundle savedInstanceState) {
        super.afterInit(savedInstanceState);
        setTitle(R.string.title_activity_my_fan_contributions);
        List<Fragment> mFragments=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long endTime=calendar.getTimeInMillis()-1;
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        long startTime=calendar.getTimeInMillis();
        if(isLive){
            startTime=this.liveStartTime;
            endTime=new Date().getTime();
        }
        mFragments.add(new MyFanContributionsFragment().setTime(EventId.FANS_CONR_DAY,startTime,endTime));
        calendar.setTimeInMillis(endTime);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        startTime=calendar.getTimeInMillis();
        mFragments.add(new MyFanContributionsFragment().setTime(EventId.FANS_CONR_WEEK,startTime,endTime));
        calendar.set(Calendar.DAY_OF_MONTH,1);
        startTime=calendar.getTimeInMillis();
        mFragments.add(new MyFanContributionsFragment().setTime(EventId.FANS_CONR_MONTH,startTime,endTime));
        calendar.setTimeInMillis(endTime);
        calendar.set(Calendar.DAY_OF_YEAR,1);
        startTime=calendar.getTimeInMillis();
        mFragments.add(new MyFanContributionsFragment().setTime(EventId.FANS_CONR_YEAR,startTime,endTime));

        String[] mTabTitles={"日榜","周榜","月榜","总榜"};
        if(isLive){
           mTabTitles= new String[]{"本次", "周榜", "月榜", "总榜"};
        }
        mViewPage.setAdapter(new SimpleFragmentAdapter(this.getSupportFragmentManager(),mFragments,mTabTitles));
        mViewPage.setOffscreenPageLimit(mFragments.size());
        mTableLayout.setupWithViewPager(mViewPage);
    }



}
