package toolbox.ll.com.toolbox.ui.live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.core.inject.BarrageAttachment;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseRListAdapter;
import toolbox.ll.com.toolbox.utils.ImageUtility;

/**
 * Created by ll on 2018/3/27.
 */

public class RoomMemberAdapter extends BaseRListAdapter<ChatRoomMember,RoomMemberAdapter.ViewHolder> {
    private Context mContext;
    public RoomMemberAdapter(Context context, List<ChatRoomMember> data) {
        this.mContext=context;
        this.setDatas(data);
    }
    @Override
    public RoomMemberAdapter.ViewHolder createCustomViewHolder(ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.live_item_member,null);
        return new RoomMemberAdapter.ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(RoomMemberAdapter.ViewHolder viewHolder, ChatRoomMember data,int postion) {
        ImageUtility.displayImage(viewHolder.mIVAvatar,data.getAvatar(),ImageUtility.TYPE_PHOTO_AVATAR);
    }

    public class ViewHolder extends BaseRListAdapter.ViewHolder{
        ImageView mIVAvatar;
        public ViewHolder(View itemView) {
            super(itemView);
            mIVAvatar=(ImageView) itemView.findViewById(R.id.item_iv_avatar);
        }
    }
}
