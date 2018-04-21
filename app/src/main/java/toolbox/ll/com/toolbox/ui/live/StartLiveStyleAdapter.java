package toolbox.ll.com.toolbox.ui.live;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessmodule.bean.LiveStyleBean;

import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.LiveMenuBean;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;

/**
 * Created by Administrator on 2018/4/21.
 */

public class StartLiveStyleAdapter extends BaseListAdapter<LiveStyleBean,StartLiveStyleAdapter.ViewHolder> {
    private Context mContext;
    public StartLiveStyleAdapter(Context context, List<LiveStyleBean> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public StartLiveStyleAdapter.ViewHolder createViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,null);
        return new StartLiveStyleAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(StartLiveStyleAdapter.ViewHolder viewHolder, LiveStyleBean data) {
        viewHolder.mTVTitle.setText(data.getLiveStyleName());
    }

    public class ViewHolder extends BaseListAdapter.ViewHolder{
        TextView mTVTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundColor(Color.TRANSPARENT);
            mTVTitle=(TextView) itemView.findViewById(android.R.id.text1);
            mTVTitle.setTextColor(mContext.getResources().getColor(R.color.hint));
            mTVTitle.setBackgroundColor(Color.TRANSPARENT);
            mTVTitle.setGravity(Gravity.CENTER);
        }
    }
}
