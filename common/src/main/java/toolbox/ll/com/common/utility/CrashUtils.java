package toolbox.ll.com.common.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * com.evideo.weiju.CrashUtils
 *
 * @author Fiona Chen <br/>
 *         Create at 2014-12-17 9:23:53 PM
 * @version V1.0.0
 */
public class CrashUtils implements UncaughtExceptionHandler {

    public static final String TAG = "CrashUtils";

    public static CrashUtils getInstance() {
        return INSTANCE;
    }

    private UncaughtExceptionHandler mDefaultHandler;
    private static CrashUtils INSTANCE = new CrashUtils();
    private Context mContext;

    private Map<String, String> infos = new HashMap<String, String>();

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashUtils() {
    }

    /**
     * collect decvice info
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * @param ex
     * @return true:deal with the exception;or return false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // use taost to show a exception info
        ToastUtils.showToast(mContext, "Oops , there was a coding error!");

        // collect device info
        collectDeviceInfo(mContext);
        // save error log
        saveCrashInfo2File(ex);
        return true;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * save error info at local file
     *
     * @param ex
     * @return file name
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        FileOutputStream fos = null;
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + "-log.txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = getLogPath() + "/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            Log.e(TAG, sb.toString());
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        } finally {
            try {
                if (fos != null)
                    fos.close();
                ;
            } catch (IOException e) {

            }

        }
        return null;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //暂时注释掉
        //BusinessInterface.getInstance().postResponse(new LogoutEvent(LogoutEvent.APP_CRASH));
        if (!handleException(ex) && mDefaultHandler != null) {
            // use default handler to deal with exception
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // TODO reload welcome activity ?  exit and kill process
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private static final String getLogPath() {
        return FileStorageUtils.getLogPath();
    }

}
