package toolbox.ll.com.toolbox.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.FanContriButionsBean;

import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;

/**
 * Created by ll on 2018/4/15.
 */

public class MyFanContributionsAdapter extends BaseRListAdapter<FanContriButionsBean,MyFanContributionsAdapter.ViewHolder> {
    public final int VIEW_TYPE_TOP=0;
    public final int VIEW_TYPE_ITEM=1;
    private Context mContext;
    public MyFanContributionsAdapter(Context context, List<FanContriButionsBean> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return VIEW_TYPE_TOP;
        return VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(mContext).inflate(viewType==VIEW_TYPE_TOP?R.layout.my_fan_contributions_item_top:R.layout.my_fan_contributions_item
                ,parent,false);
        return new MyFanContributionsAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(ViewHolder viewHolder, FanContriButionsBean data) {

    }

    public class ViewHolder extends BaseRListAdapter.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}



