package toolbox.ll.com.common.utility;

import android.text.TextUtils;

public class QiniuUtil {
	
	private static String THUMBNAIL = "?imageView2/2/w/256/h/256/q/66";
	private static String THUMBNAIL_M = "?imageView2/2/w/512/h/512/q/75";
	
	public static String getThumbnailUrl(String url){
		if(TextUtils.isEmpty(url))
			return "";
		return url + THUMBNAIL;
	}
	
	public static String getThumbnailMediumUrl(String url){
		if(TextUtils.isEmpty(url))
			return "";
		return url + THUMBNAIL_M;
	}



}
