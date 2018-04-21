package toolbox.ll.com.toolbox.ui.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessmodule.bean.FansContriButionsBean;
import com.example.businessmodule.bean.InComeBean;

import java.util.ArrayList;
import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;
import toolbox.ll.com.toolbox.utils.ImageUtility;

/**
 * Created by ll on 2018/4/15.
 */

public class MyFanContributionsAdapter extends BaseRListAdapter<FansContriButionsBean,MyFanContributionsAdapter.ViewHolder> {
    public final int VIEW_TYPE_TOP=0;
    public final int VIEW_TYPE_ITEM=1;
    private Context mContext;
    public MyFanContributionsAdapter(Context context, List<FansContriButionsBean> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return VIEW_TYPE_TOP;
        return VIEW_TYPE_ITEM;
    }

    public void addData(List<FansContriButionsBean> list){
        if(list==null)
            return;
        List<FansContriButionsBean> data=getmDatas();
        if(data==null){
            data=new ArrayList<>();
        }
        data.addAll(list);
        setDatas(data);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(mContext).inflate(viewType==VIEW_TYPE_TOP?R.layout.my_fan_contributions_item_top:R.layout.my_fan_contributions_item
                ,parent,false);
        return new MyFanContributionsAdapter.ViewHolder(view);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void bindDataToViewHolder(ViewHolder viewHolder, FansContriButionsBean data,int position) {
        ImageUtility.displayImage(viewHolder.mIVAvatar,data.getIcon(),ImageUtility.TYPE_PHOTO_AVATAR);
        viewHolder.mTVNick.setText(data.getNick());
        viewHolder.mTVCoin.setText(String.format(mContext.getResources().getString(R.string.fanContr_coin),data.getContributeCoin()));
        if(viewHolder.mTVNO!=null)
            viewHolder.mTVNO.setText(position+1+"");
        if(viewHolder.mIVCrown!=null){
            if(position==1){
                viewHolder.mIVCrown.setVisibility(View.VISIBLE);
                viewHolder.mIVCrown.setImageResource(R.drawable.ic_crown_2);
            }else if(position==2){
                viewHolder.mIVCrown.setVisibility(View.VISIBLE);
                viewHolder.mIVCrown.setImageResource(R.drawable.ic_crown_3);
            } else
                viewHolder.mIVCrown.setVisibility(View.INVISIBLE);
        }
    }

    public class ViewHolder extends BaseRListAdapter.ViewHolder{
        ImageView mIVAvatar;
        TextView mTVNick;
        TextView mTVCoin;
        TextView mTVNO;
        ImageView mIVCrown;

        public ViewHolder(View itemView) {
            super(itemView);
            mIVAvatar=(ImageView)itemView.findViewById(R.id.item_iv_avatar);
            mTVNick=(TextView)itemView.findViewById(R.id.item_tv_name);
            mTVCoin=(TextView)itemView.findViewById(R.id.item_tv_coin);
            mTVNO=(TextView)itemView.findViewById(R.id.item_tv_no);
            mIVCrown=(ImageView)itemView.findViewById(R.id.item_iv_crown);
        }
    }

}



