package toolbox.ll.com.common.utility;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by root on 2016/9/1.
 */
public class FileUtils {
    public static boolean isFileExit(String path){
        if(StringUtils.isEmpty(path))
            return false;
        File file=new File(path);
        return file.exists() && file.length() > 0;
    }

    public static String getFileName(String url){
        if(StringUtils.isEmpty(url))
            return "";
        if(!url.contains("/")||!url.contains("."))
            return "";
        return url.substring(url.lastIndexOf("/")+1,url.length());

    }

    public static String getImageName(String url){
        return getFileName(url)+".jpg";

    }

    public static String readFile(InputStream inputStream) {
        String result="";
        byte[] buffer=new byte[1024];
        int len=-1;
        int curSize=0;
        int flag=0;
        try {
            while ((len=inputStream.read(buffer))>0) {
                result +=new String(buffer,0,len);
            }
        } catch (IOException e) {
            Logger.i(e.getMessage());
            Logger.e(e.getMessage());
            return null;
        }finally {
            if(inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;


    }


    public static void writeToFile(InputStream inputStream, File file, long perSize, FileWriteListener listener) throws IOException {
        OutputStream outputStream=null;
        try{
            file.deleteOnExit();
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            outputStream=new FileOutputStream(file);
            byte[] buffer=new byte[1024*256];
            int len=-1;
            int curSize=0;
            int flag=0;
            while ((len=inputStream.read(buffer))>0){
                outputStream.write(buffer,0,len);
                curSize+=len;
                if(listener!=null&&curSize>flag+perSize){
                    listener.progress(curSize);
                    flag=curSize;
                }

            }
            if(listener!=null)
                listener.finish();
        }catch (IOException e){
            throw e;
        }finally {
            if(outputStream!=null)
                outputStream.close();
        }

    }

    public static void writeToFile(InputStream inputStream, File file, FileWriteListener listener) throws IOException {
        OutputStream outputStream=null;
        try {
            file.deleteOnExit();
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
             outputStream=new FileOutputStream(file);
            byte[] buffer=new byte[1024*256];
            int len=-1;
            int curSize=0;
            while ((len=inputStream.read(buffer))>0){
                outputStream.write(buffer,0,len);
                curSize+=len;
                if(listener!=null)
                    listener.progress(curSize);
            }
            if(listener!=null)
                listener.finish();
        }catch (IOException e){
            throw  e;
        }finally {
            if(outputStream!=null)
                outputStream.close();
        }

    }

    public interface  FileWriteListener{
        void progress(long curSize);
        void finish();
    }

}
