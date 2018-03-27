package toolbox.ll.com.toolbox.core.inject;

import android.app.Activity;

import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

import toolbox.ll.com.toolbox.ui.live.LiveStreamingActivity;

/**
 * Created by huangjun on 2016/3/15.
 */
public class FlavorDependent implements IFlavorDependent{
    @Override
    public String getFlavorName() {
        return "entertainment";
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return LiveStreamingActivity.class;
    }

    @Override
    public MsgAttachmentParser getMsgAttachmentParser() {
        return new CustomAttachParser();
    }

    @Override
    public void onLogout() {
    }

    public static FlavorDependent getInstance() {
        return InstanceHolder.instance;
    }

    public static class InstanceHolder {
        public final static FlavorDependent instance = new FlavorDependent();
    }

    @Override
    public void onApplicationCreate() {

    }
}
