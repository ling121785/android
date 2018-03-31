package toolbox.ll.com.toolbox.bean;

import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.vcloud.video.effect.VideoEffect;

import java.io.Serializable;

import static com.netease.LSMediaCapture.lsMediaCapture.FormatType.RTMP;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AV;

/**
 * Created by ll on 2018/3/24.
 */

public class LiveStreamingBean implements Serializable {
    private String liveId=null;
    private String  roomId="22409361";
    private String pushUrl = "rtmp://p6420c350.live.126.net/live/269cb7fda1604664ad8b795abc701e7c?wsSecret=15e53c0f9162e0c2b9be6f7db4c060fb&wsTime=1521864586"; //推流地址
    private lsMediaCapture.StreamType streamType = AV;  // 推流类型
    private lsMediaCapture.FormatType formatType = RTMP; // 推流格式
    private String recordPath; //文件录制地址，当formatType 为 MP4 或 RTMP_AND_MP4 时有效
    private lsMediaCapture.VideoQuality videoQuality = lsMediaCapture.VideoQuality.SUPER; //清晰度
    private boolean isScale_16x9 = false; //是否强制16:9
    private boolean useFilter = true; //是否使用滤镜
    private VideoEffect.FilterType filterType = VideoEffect.FilterType.clean; //滤镜类型
    private boolean frontCamera = true; //是否默认前置摄像头
    private boolean watermark = false; //是否添加水印
    private boolean qosEnable = true;  //是否开启QOS
    private int qosEncodeMode = 1; // 1:流畅优先, 2:清晰优先
    private boolean graffitiOn = false; //是否添加涂鸦
    private  boolean uploadLog = false; //是否上传SDK运行日志

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public lsMediaCapture.StreamType getStreamType() {
        return streamType;
    }

    public void setStreamType(lsMediaCapture.StreamType streamType) {
        this.streamType = streamType;
    }

    public lsMediaCapture.FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(lsMediaCapture.FormatType formatType) {
        this.formatType = formatType;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public lsMediaCapture.VideoQuality getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(lsMediaCapture.VideoQuality videoQuality) {
        this.videoQuality = videoQuality;
    }

    public boolean isScale_16x9() {
        return isScale_16x9;
    }

    public void setScale_16x9(boolean scale_16x9) {
        isScale_16x9 = scale_16x9;
    }

    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }

    public VideoEffect.FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(VideoEffect.FilterType filterType) {
        this.filterType = filterType;
    }

    public boolean isFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(boolean frontCamera) {
        this.frontCamera = frontCamera;
    }

    public boolean isWatermark() {
        return watermark;
    }

    public void setWatermark(boolean watermark) {
        this.watermark = watermark;
    }

    public boolean isQosEnable() {
        return qosEnable;
    }

    public void setQosEnable(boolean qosEnable) {
        this.qosEnable = qosEnable;
    }

    public int getQosEncodeMode() {
        return qosEncodeMode;
    }

    public void setQosEncodeMode(int qosEncodeMode) {
        this.qosEncodeMode = qosEncodeMode;
    }

    public boolean isGraffitiOn() {
        return graffitiOn;
    }

    public void setGraffitiOn(boolean graffitiOn) {
        this.graffitiOn = graffitiOn;
    }

    public boolean isUploadLog() {
        return uploadLog;
    }

    public void setUploadLog(boolean uploadLog) {
        this.uploadLog = uploadLog;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }
}
