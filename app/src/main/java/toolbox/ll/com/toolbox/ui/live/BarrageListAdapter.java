package toolbox.ll.com.toolbox.ui.live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;

import java.util.ArrayList;
import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.MainMenu;
import toolbox.ll.com.toolbox.core.inject.BarrageAttachment;
import toolbox.ll.com.toolbox.core.inject.GiftAttachment;
import toolbox.ll.com.toolbox.ui.MainMenuAdapter;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;

/**
 * Created by ll on 2018/3/27.
 */

public class BarrageListAdapter extends BaseListAdapter<ChatRoomMessage,BarrageListAdapter.ViewHolder> {
    private Context mContext;
    public BarrageListAdapter(Context context, List<ChatRoomMessage> data) {
        this.mContext=context;
        this.setDatas(data);
    }
    public void add(ChatRoomMessage msg){
        List<ChatRoomMessage> data=getmDatas();
        if(data==null){
            data=new ArrayList<ChatRoomMessage>();
        }
        data.add(msg);
        setDatas(data);
        this.notifyDataSetChanged();
    }

    @Override
    public BarrageListAdapter.ViewHolder createViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.live_item_barrage,null);
        return new BarrageListAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(BarrageListAdapter.ViewHolder viewHolder, ChatRoomMessage data) {
        String msg="";
        if(data.getAttachment()instanceof BarrageAttachment){
            msg=data.getFromAccount()+":";
            BarrageAttachment attachment=(BarrageAttachment)data.getAttachment();
            msg+=attachment.getData().getText();
        }else if(data.getAttachment() instanceof ChatRoomNotificationAttachment){
            ChatRoomNotificationAttachment attachment = (ChatRoomNotificationAttachment) data.getAttachment();
            msg=attachment.getTargetNicks()+"进入房间啦";
        }
        viewHolder.mTVContent.setText(msg);

    }

    public class ViewHolder extends BaseListAdapter.ViewHolder{
        TextView mTVContent;
        public ViewHolder(View itemView) {
            super(itemView);
            mTVContent=(TextView) itemView.findViewById(R.id.item_tv_content);
        }
    }
}
