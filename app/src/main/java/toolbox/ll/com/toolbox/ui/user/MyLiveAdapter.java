package toolbox.ll.com.toolbox.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessmodule.bean.InComeBean;
import com.example.businessmodule.bean.LiveBean;

import java.util.ArrayList;
import java.util.List;

import toolbox.ll.com.common.utility.DateUtils;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;

/**
 * Created by ll on 2018/3/27.
 */

public class MyLiveAdapter extends BaseRListAdapter<LiveBean,MyLiveAdapter.ViewHolder> {
    private Context mContext;
    public MyLiveAdapter(Context context, List<LiveBean> data,OnItemClickListener listener) {
        super(context, data, listener);
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
        viewHolder.mTVdate.setText(DateUtils.formateTime(data.getStartTime()*1000,"yyyy-MM-dd HH:mm"));
        long during=data.getEndTime()-data.getStartTime();
        during=during<0?0:during;
        viewHolder.mTVDuring.setText(DateUtils.formatCallDuration(during));
        viewHolder.mLayoutMore.setVisibility(data.isCheck()?View.VISIBLE:View.GONE);
        viewHolder.mTVLikeNum.setText(data.getLikeNum()+"");
        viewHolder.mTVWatchNum.setText(data.getWatchNum()+"");
        viewHolder.mTVNewCoin.setText(data.getCoin()+"");
        viewHolder.mTVNewFans.setText(data.getFansNum()+"");
        viewHolder.mIVMore.setImageResource(data.isCheck()?R.drawable.ic_down:R.drawable.ic_more);
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
        TextView mTVDuring;
        View mLayoutMore;
        TextView mTVWatchNum;
        TextView mTVNewFans;
        TextView mTVLikeNum;
        TextView mTVNewCoin;
        ImageView mIVMore;
        public ViewHolder(View itemView) {
            super(itemView);
            mTVdate=(TextView) itemView.findViewById(R.id.income_tv_date);
            mTVDuring=(TextView) itemView.findViewById(R.id.item_tv_during);
            mTVWatchNum=(TextView) itemView.findViewById(R.id.item_tv_watchNum);
            mTVNewFans=(TextView) itemView.findViewById(R.id.item_tv_newFans);
            mTVLikeNum=(TextView) itemView.findViewById(R.id.item_tv_likeNum);
            mTVNewCoin=(TextView) itemView.findViewById(R.id.item_tv_newCoin);
            mLayoutMore=itemView.findViewById(R.id.item_layout_more);
            mIVMore=(ImageView)itemView.findViewById(R.id.item_iv_toggle);
        }
    }
}
