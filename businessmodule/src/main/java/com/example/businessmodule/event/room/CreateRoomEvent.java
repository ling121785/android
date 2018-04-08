package com.example.businessmodule.event.room;

import com.example.businessmodule.bean.RoomBean;
import com.example.businessmodule.event.BaseEvent;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class CreateRoomEvent extends BaseEvent<CreateRoomEvent.Request,RoomBean> {
    public CreateRoomEvent(long eventId,String roomName,String extraMessage,String poster) {
        super(eventId);
        setRequest(new Request(roomName,extraMessage,poster));
    }

    public class Request{
        @SerializedName("live_name")
        private String roomName;
        @SerializedName("live_announcement")
        private String extraMessage;
        @SerializedName("poster")
        private String poster;

        public Request(String roomName, String extraMessage,String poster) {
            this.roomName = roomName;
            this.extraMessage = extraMessage;
            this.poster=poster;
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

    public interface Rest {
        @POST("live")
        Observable<RoomBean> request(@Query("access_token") String token, @Body Request roomConfig);
    }
}
