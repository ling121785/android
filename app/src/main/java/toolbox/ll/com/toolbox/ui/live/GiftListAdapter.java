package toolbox.ll.com.toolbox.ui.live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessmodule.bean.GiftBean;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.room.GiftListEvent;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import toolbox.ll.com.common.utility.StringUtils;
import toolbox.ll.com.common.widget.CircleImageView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.core.inject.BarrageAttachment;
import toolbox.ll.com.toolbox.core.inject.GiftAttachment;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;
import toolbox.ll.com.toolbox.utils.ImageUtility;

/**
 * Created by ll on 2018/3/27.
 */

public class GiftListAdapter extends BaseListAdapter<ChatRoomMessage,GiftListAdapter.ViewHolder> {
    private Context mContext;
    public GiftListAdapter(Context context, List<ChatRoomMessage> data) {
        this.mContext=context;
        this.setDatas(data);
    }
    public void add(ChatRoomMessage msg){
        List<ChatRoomMessage> data=getmDatas();
        if(data==null){
            data=new ArrayList<ChatRoomMessage>();
        }
        data.add(msg);
        if(data.size()>50){
            data.remove(0);
        }
        setDatas(data);
        this.notifyDataSetChanged();
    }

    @Override
    public GiftListAdapter.ViewHolder createViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.live_item_gift,null);
        return new GiftListAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(GiftListAdapter.ViewHolder viewHolder, ChatRoomMessage data) {
        if(data.getAttachment()instanceof GiftAttachment){
            GiftAttachment attachment=(GiftAttachment)data.getAttachment();
            GiftBean gift=getGiftBean(attachment.getData().getId());
            String nick= StringUtils.getString(data.getFromNick());
            String desc="送出玫瑰";
            if(gift!=null){
                desc="送出"+gift.getName();
            }
            String num="X"+attachment.getData().getNum();
            viewHolder.mTVNick.setText(nick);
            viewHolder.mTVDesc.setText(desc);
            viewHolder.mTVCount.setText(num);
            ImageUtility.displayImage(viewHolder.mIVGift,gift==null?"":gift.getIcon(),ImageUtility.TYPE_GIFT);
        }
    }
    private GiftBean getGiftBean(int id){
        List<GiftBean> list= BusinessSession.getInstance().getmGiftList();
        if(list==null)
            return null;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getId()==id)
                return list.get(i);
        }
        return null;
    }

    public class ViewHolder extends BaseListAdapter.ViewHolder{
        CircleImageView mIVAvatar;
        TextView mTVNick;
        TextView mTVDesc;
        TextView mTVCount;
        ImageView mIVGift;
        public ViewHolder(View itemView) {
            super(itemView);
            mIVAvatar=(CircleImageView) itemView.findViewById(R.id.item_iv_avatar);
            mIVGift=(ImageView) itemView.findViewById(R.id.item_iv_gift);
            mTVNick=(TextView) itemView.findViewById(R.id.item_tv_nick);
            mTVDesc=(TextView) itemView.findViewById(R.id.item_tv_desc);
            mTVCount=(TextView) itemView.findViewById(R.id.item_tv_num);
        }
    }
}
