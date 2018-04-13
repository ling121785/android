package toolbox.ll.com.toolbox.ui.user;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
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
    @Override
    public void initView() {
        super.initView();
        addContent(R.layout.activity_my_fan_contributions);
    }
    @Override
    public void afterInit(Bundle savedInstanceState) {
        super.afterInit(savedInstanceState);
        setTitle(R.string.title_activity_my_fan_contributions);
        List<Fragment> mFragments=new ArrayList<>();
        mFragments.add(new MyFanContributionsFragment());
        mFragments.add(new MyFanContributionsFragment());
        mFragments.add(new MyFanContributionsFragment());
        mFragments.add(new MyFanContributionsFragment());
        String[] mTabTitles={"日版","月版","周版","总榜"};
        mViewPage.setAdapter(new SimpleFragmentAdapter(this.getSupportFragmentManager(),mFragments,mTabTitles));
        mTableLayout.setupWithViewPager(mViewPage);
    }



}
