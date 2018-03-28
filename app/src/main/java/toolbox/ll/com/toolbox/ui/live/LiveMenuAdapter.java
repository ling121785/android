package toolbox.ll.com.toolbox.ui.live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;

import java.util.ArrayList;
import java.util.List;

import toolbox.ll.com.common.widget.CircleImageView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.LiveMenuBean;
import toolbox.ll.com.toolbox.core.inject.GiftAttachment;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;

/**
 * Created by ll on 2018/3/27.
 */

public class LiveMenuAdapter extends BaseListAdapter<LiveMenuBean,LiveMenuAdapter.ViewHolder> {
    private Context mContext;
    public LiveMenuAdapter(Context context, List<LiveMenuBean> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public LiveMenuAdapter.ViewHolder createViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.live_item_menu,null);
        return new LiveMenuAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(LiveMenuAdapter.ViewHolder viewHolder, LiveMenuBean data) {
        viewHolder.mTVTitle.setText(data.getTitle());
        viewHolder.mIVICon.setImageResource(data.getImgId());
    }

    public class ViewHolder extends BaseListAdapter.ViewHolder{
        TextView mTVTitle;
        ImageView mIVICon;
        public ViewHolder(View itemView) {
            super(itemView);
            mIVICon=(ImageView) itemView.findViewById(R.id.live_menu_iv_icon);
            mTVTitle=(TextView) itemView.findViewById(R.id.live_menu_tv_title);
        }
    }
}
