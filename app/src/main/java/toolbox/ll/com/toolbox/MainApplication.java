package toolbox.ll.com.toolbox;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.businessmodule.core.BusinessInterface;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.util.NIMUtil;

import toolbox.ll.com.common.utility.CrashUtils;
import toolbox.ll.com.toolbox.utils.NeteaseUtil;

/**
 * Created by Administrator on 2018/3/20.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashUtils.getInstance().init(this);
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, NeteaseUtil.loginInfo(), NeteaseUtil.options(this));
        //初始化业务
        BusinessInterface.getInstance().init(this,true,true,this.getPackageName(),"");
        // ... your codes
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}