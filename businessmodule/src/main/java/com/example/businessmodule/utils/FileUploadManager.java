package com.example.businessmodule.utils;

import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by root on 2016/6/27.
 */
public class FileUploadManager {
    private static FileUploadManager instance;
    private UploadManager mUploadManager=null;
    private Configuration mConfig;


    private FileUploadManager(){

        mConfig = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
//                .recorder(recorder)  // recorder 分片上传时，已上传片记录器。默认 null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        mUploadManager=new UploadManager(mConfig);
    }


    public void uploadFiles(List<String> fileList, String token, UpCompletionHandler upCompletionHandler, UploadOptions uploadOptions){
        List<String> urls=new ArrayList<String>();
        for(String filePath:fileList){
            File file=new File(filePath);
            String key= UUID.randomUUID().toString();
            if(file.exists()){
                mUploadManager.put(file, key, token,upCompletionHandler,uploadOptions);
            }
        }
    }

    public void uploadFiles(File file, String token, UpCompletionHandler upCompletionHandler, UploadOptions uploadOptions){
            String key= UUID.randomUUID().toString();
            if(file.exists()){
                mUploadManager.put(file, key, token,upCompletionHandler,uploadOptions);
            }

    }

    public static FileUploadManager getInstance(){
        if(instance==null)
            instance=new FileUploadManager();
        return instance;
    }

}
