package com.example.businessmodule.event.roomBusiness;

import com.example.businessmodule.event.BaseEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;

/**
 * Created by Administrator on 2018/3/22.
 */

public class CreateRoomEvent extends BaseEvent<CreateRoomEvent.Request,AVChatChannelInfo> {
    public CreateRoomEvent(long eventId,String roomName,String extraMessage) {
        super(eventId);
        setRequest(new Request(roomName,extraMessage));
    }

    public class Request{
        private String roomName;
        private String extraMessage;

        public Request(String roomName, String extraMessage) {
            this.roomName = roomName;
            this.extraMessage = extraMessage;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public void setExtraMessage(String extraMessage) {
            this.extraMessage = extraMessage;
        }
    }
}
