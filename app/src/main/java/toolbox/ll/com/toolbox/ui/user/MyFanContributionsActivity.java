package toolbox.ll.com.toolbox.ui.user;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseTitleActivity;

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
    }
}
