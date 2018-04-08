package toolbox.ll.com.toolbox.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.InComeBean;

import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;

/**
 * Created by ll on 2018/3/27.
 */

public class MyFansAdapter extends BaseRListAdapter<AccountBean,MyFansAdapter.ViewHolder> {
    private Context mContext;
    public MyFansAdapter(Context context, List<AccountBean> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public MyFansAdapter.ViewHolder createViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fans_item,parent,false);
        return new MyFansAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(MyFansAdapter.ViewHolder viewHolder, AccountBean data) {

    }

    public class ViewHolder extends BaseRListAdapter.ViewHolder{
        ImageView mIVAvatar;
        TextView mTVNick;
        TextView mTVDesc;
        public ViewHolder(View itemView) {
            super(itemView);
            mIVAvatar=(ImageView) itemView.findViewById(R.id.item_iv_avatar) ;
            mTVNick=(TextView) itemView.findViewById(R.id.item_tv_nick);
            mTVDesc=(TextView) itemView.findViewById(R.id.item_tv_desc);
        }
    }
}
