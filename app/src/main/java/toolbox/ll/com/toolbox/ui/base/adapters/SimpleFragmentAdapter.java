package toolbox.ll.com.toolbox.ui.base.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class SimpleFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;

    public SimpleFragmentAdapter(FragmentManager fm,List<Fragment> fragments,String[] mTitles) {
        super(fm);
        this.mFragments=fragments;
        this.mTitles=mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles!=null&&mTitles.length>position)
            return mTitles[position];
        return super.getPageTitle(position);
    }
}