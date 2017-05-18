package toolbox.ll.com.common.utility;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 文件/目录相关工具
 */
public class FileStorageUtils {

    public static final String SNAPSHOT_SUB_PATH = "snapshot/";
    public static final String WAVE_SUB_PATH = "wave/";
    public static final String APK_PATH = "apks/";
    public static final String LOG_SUB_PATH = "resident-log/";
    public static final String CAMERAL_PATH = "photo/";
    public static final String TEMP_IMG_PATH = "temp/";
    public static final String DOWN_IMG_PATH = "img/";
    public static final String WEB_PAGE_VERSION_PATH="www/version.txt";
    public static final String APP_NAME="toolBox";

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private static String getSdCardWorkPath() {
        String workPath = null;
        if (isSdCardExist())
            workPath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + APP_NAME + File.separator;
        return workPath;
    }



    public static String getSnapshotPath() {
        return getSdCardWorkPath() + SNAPSHOT_SUB_PATH;
    }

    public static String getWavePathSD() {
        return getSdCardWorkPath() + WAVE_SUB_PATH;
    }

    public static String getWavePath(Context context) {
//        if(context==null)
//            return  getWavePathSD();
        return context.getExternalCacheDir().getAbsolutePath();
    }



    public static String getApkPath() {
        return getSdCardWorkPath() + APK_PATH;
    }

    public static String getLogPath() {
        return getSdCardWorkPath() + LOG_SUB_PATH;
    }

    public static String getTempPath() {
        return getSdCardWorkPath() + TEMP_IMG_PATH;
    }

    public static String getImageSavePath() {
        return getSdCardWorkPath() + DOWN_IMG_PATH;
    }

    public static String getPhotoPath() {
        try {
            File file = new File(getSnapshotPath() + CAMERAL_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {

        }
        return getSnapshotPath() + CAMERAL_PATH;
    }

    public static String getWebPagePath(Context context){
        return context.getFilesDir().getAbsolutePath();
    }
}
