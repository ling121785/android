package toolbox.ll.com.toolbox.core.inject;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

import toolbox.ll.com.common.utility.JsonUtils;


/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            com.orhanobut.logger.Logger.i("收到消息"+json);
            JSONObject object = JSON.parseObject(json);
            String type = object.getString(KEY_TYPE);
            JSONObject data = object.getJSONObject(KEY_DATA);
            com.orhanobut.logger.Logger.i("收到消息"+type);
            com.orhanobut.logger.Logger.i("收到消息"+json);
            switch (type) {
                case CustomAttachmentType.GIFT:
                    attachment = JsonUtils.jsonToObj(json,GiftAttachment.class);
                    break;
                case CustomAttachmentType.BARRAGE:
                    attachment =  JsonUtils.jsonToObj(json,BarrageAttachment.class);
                    break;
                case CustomAttachmentType.COIN_CHANGE:
                    attachment=JsonUtils.jsonToObj(json,CoinChangeAttachment.class);

//                default:
//                    attachment = new DefaultCustomAttachment();
//                    break;
            }
        } catch (Exception e) {

        }

        return attachment;
    }

}
