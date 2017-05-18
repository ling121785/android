package toolbox.ll.com.common.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.List;

import toolbox.ll.com.common.BuildConfig;

public class DeviceUtils {
    public static final String XiaoMi = "xiaomi";
    public static final String HuaWei = "huawei";

    public static String getDeviceId(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
    public static boolean isApplicationBroughtToBackground(Context context) {
        if (context == null) {
            return false;
        }
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIntentSafe(Activity activity, Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }


    public static String getCurrentVersionName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (BuildConfig.DEBUG)
                return info.versionName + "_debug";
            else
                return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Error";
        }
    }

    public static int getCurrentVersionCode(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    public static String getCurSys() {
        return Build.MANUFACTURER;
    }

    public static boolean isXiaoMi() {
        String curSys = getCurSys().toLowerCase();
        return curSys.contains(XiaoMi);
    }

    public static boolean isHuaWei() {
        String curSys = getCurSys().toLowerCase();
        return curSys.contains(HuaWei);
    }

    public static String getUUID(Context context,String singleId) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId,phoneNum;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString
                (context.getContentResolver()
                        , android.provider.Settings.Secure.ANDROID_ID);
        phoneNum=singleId;
        if(phoneNum==null)
        return null;
        return Md5Utils.encodeMd5("danbaofan" + tmDevice + tmSerial + androidId+phoneNum);
    }

    
}
