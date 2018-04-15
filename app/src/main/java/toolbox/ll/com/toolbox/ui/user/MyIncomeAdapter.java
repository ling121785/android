package toolbox.ll.com.toolbox.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessmodule.bean.InComeBean;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;

import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;
import toolbox.ll.com.toolbox.utils.ImageUtility;

/**
 * Created by ll on 2018/3/27.
 */

public class MyIncomeAdapter extends BaseRListAdapter<InComeBean,MyIncomeAdapter.ViewHolder> {
    private Context mContext;
    public MyIncomeAdapter(Context context, List<InComeBean> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public MyIncomeAdapter.ViewHolder createCustomViewHolder(ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.income_item,parent,false);
        return new MyIncomeAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(MyIncomeAdapter.ViewHolder viewHolder, InComeBean data) {

    }

    public class ViewHolder extends BaseRListAdapter.ViewHolder{
        TextView mTVdate;
        TextView mTVcoin;
        public ViewHolder(View itemView) {
            super(itemView);
            mTVdate=(TextView) itemView.findViewById(R.id.income_tv_date);
            mTVcoin=(TextView) itemView.findViewById(R.id.income_tv_coin);
        }
    }
}
