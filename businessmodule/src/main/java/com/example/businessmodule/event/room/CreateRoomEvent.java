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
    public CreateRoomEvent(long eventId,String roomName, String extraMessage, String poster, Integer styleType, Integer liveCharge, String livePwd, Integer liveMaxNumber) {
        super(eventId);
        setRequest(new Request( roomName,  extraMessage,  poster,  styleType,  liveCharge,livePwd,liveMaxNumber));
    }

    public class Request{
        @SerializedName("live_name")
        private String roomName;
        @SerializedName("live_announcement")
        private String extraMessage;
        @SerializedName("poster")
        private String poster;

        @SerializedName("live_type")
        private Integer styleType;

        @SerializedName("live_charge")
        private Integer liveCharge;

        @SerializedName("live_pwd")
        private String livePwd;

        @SerializedName("live_max_number")
        private Integer liveMaxNumber;

        public Request(String roomName, String extraMessage, String poster, Integer styleType, Integer liveCharge, String livePwd, Integer liveMaxNumber) {
            this.roomName = roomName;
            this.extraMessage = extraMessage;
            this.poster = poster;
            this.styleType = styleType;
            this.liveCharge = liveCharge;
            this.livePwd = livePwd;
            this.liveMaxNumber = liveMaxNumber;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public Integer getStyleType() {
            return styleType;
        }

        public void setStyleType(Integer styleType) {
            this.styleType = styleType;
        }

        public Integer getLiveCharge() {
            return liveCharge;
        }

        public void setLiveCharge(Integer liveCharge) {
            this.liveCharge = liveCharge;
        }

        public String getLivePwd() {
            return livePwd;
        }

        public void setLivePwd(String livePwd) {
            this.livePwd = livePwd;
        }

        public Integer getLiveMaxNumber() {
            return liveMaxNumber;
        }

        public void setLiveMaxNumber(Integer liveMaxNumber) {
            this.liveMaxNumber = liveMaxNumber;
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
