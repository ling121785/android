package toolbox.ll.com.toolbox.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.businessmodule.bean.InComeBean;
import com.example.businessmodule.bean.LiveBean;

import java.util.ArrayList;
import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;

/**
 * Created by ll on 2018/3/27.
 */

public class MyLiveAdapter extends BaseRListAdapter<LiveBean,MyLiveAdapter.ViewHolder> {
    private Context mContext;
    public MyLiveAdapter(Context context, List<LiveBean> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public MyLiveAdapter.ViewHolder createCustomViewHolder(ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.live_item,parent,false);
        return new MyLiveAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(MyLiveAdapter.ViewHolder viewHolder, LiveBean data ,int postion) {

    }
    public void addData(List<LiveBean> list){
        if(list==null)
            return;
        List<LiveBean> data=getmDatas();
        if(data==null){
            data=new ArrayList<>();
        }
        data.addAll(list);
        setDatas(data);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends BaseRListAdapter.ViewHolder{
        TextView mTVdate;
        public ViewHolder(View itemView) {
            super(itemView);
            mTVdate=(TextView) itemView.findViewById(R.id.income_tv_date);
        }
    }
}
