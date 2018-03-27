package toolbox.ll.com.toolbox.core.inject;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonNull;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

import toolbox.ll.com.common.utility.JsonUtils;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public abstract class CustomAttachment<T> implements MsgAttachment {

    protected String type;
    protected T data;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toJson(boolean send) {
        return JsonUtils.objToJson(this);
    }

}
