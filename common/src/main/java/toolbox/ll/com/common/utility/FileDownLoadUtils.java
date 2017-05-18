package toolbox.ll.com.common.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownLoadUtils {

    /**
     * http下载接口
     *
     * @param urlStr
     * @param path     本地目录
     * @param fileName 保存的本地文件名
     * @param recover  是否覆盖已存在的文件
     * @return 1:success, 0:exist, -1:fail, -2:toast size error
     */
    public static int downloadFile(String urlStr, String path, String fileName, boolean recover) {
        if (!path.endsWith(File.separator))
            path = path + File.separator;
        File dstFile = new File(path + fileName);
        File dir = dstFile.getParentFile();
        if (!dir.exists() && !dir.mkdirs()) {
            return -1;
        }
        if (dstFile.exists()) {
            if (!recover) {
                return 0;
            } else {
                dstFile.delete();
            }
        }
        OutputStream output = null;
        try {
            if (!dstFile.exists()) {
                dstFile.createNewFile();
            }
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream input = conn.getInputStream();
            long total = conn.getContentLength();
            output = new FileOutputStream(dstFile);
            byte[] buffer = new byte[8 * 1024];
            int ret;
            while ((ret = input.read(buffer)) != -1) {
                output.write(buffer, 0, ret);
                total -= ret;
            }
            output.flush();
            input.close();
            if (total > 0) {
                dstFile.delete();
                return -2;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            dstFile.delete();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            dstFile.delete();
            return -1;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    /**
     * http下载接口
     *
     * @param urlStr
     * @param recover  是否覆盖已存在的文件
     * @return 1:success, 0:exist, -1:fail, -2:toast size error
     */
    public static int downloadFile(String urlStr, String savePath, boolean recover) {
        File dstFile = new File(savePath);
        File dir = dstFile.getParentFile();
        if (!dir.exists() && !dir.mkdirs()) {
            return -1;
        }
        if (dstFile.exists()) {
            if (!recover) {
                return 0;
            } else {
                dstFile.delete();
            }
        }
        OutputStream output = null;
        try {
            if (!dstFile.exists()) {
                dstFile.createNewFile();
            }
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream input = conn.getInputStream();
            long total = conn.getContentLength();
            output = new FileOutputStream(dstFile);
            byte[] buffer = new byte[8 * 1024];
            int ret;
            while ((ret = input.read(buffer)) != -1) {
                output.write(buffer, 0, ret);
                total -= ret;
            }
            output.flush();
            input.close();
            if (total > 0) {
                dstFile.delete();
                return -2;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            dstFile.delete();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            dstFile.delete();
            return -1;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }
}
