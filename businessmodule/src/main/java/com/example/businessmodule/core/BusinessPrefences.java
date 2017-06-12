package com.example.businessmodule.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.businessmodule.exception.PrefenceException;
import com.orhanobut.logger.Logger;

public class BusinessPrefences {

	private static final String PREF_NAME = "business.pref";

	private static BusinessPrefences instance = null;
	public static BusinessPrefences getInstance() {
		if (instance == null) {
			instance = new BusinessPrefences();
		}
		return instance;
	}

	private SharedPreferences preferences = null;
	public void init(Context context) {
		preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	private void saveSettings(String key, Object value) throws PrefenceException {
		if (preferences == null)
			throw new PrefenceException("prefrence is not inited.");

		Editor editor = preferences.edit();
		if (value instanceof Integer)
			editor.putInt(key, (Integer) value);
		else if (value instanceof Long)
			editor.putLong(key, (Long) value);
		else if (value instanceof Boolean)
			editor.putBoolean(key, (Boolean) value);
		else if (value instanceof String)
			editor.putString(key, (String) value);
		else if (value instanceof Float)
			editor.putFloat(key, (Float) value);
		editor.commit();
	}

	private int getSettingsInt(String key, int defaultValue) throws PrefenceException {
		if (preferences == null) throw new PrefenceException("prefrence is not inited.");
		return preferences.getInt(key, defaultValue);
	}

	private long getSettingsLong(String key, Long defaultValue) throws PrefenceException{
		if (preferences == null) throw new PrefenceException("prefrence is not inited.");
		return preferences.getLong(key, defaultValue);
	}

	private boolean getSettingsBoolean(String key, boolean defaultValue) throws PrefenceException{
		if (preferences == null) throw new PrefenceException("prefrence is not inited.");
		return preferences.getBoolean(key, defaultValue);
	}

	private String getSettingsString(String key, String defaultValue) throws PrefenceException{
		if (preferences == null) throw new PrefenceException("prefrence is not inited.");
		return preferences.getString(key, defaultValue);
	}








	/********************  appconfig prefences *********************/
	private static final String KEY_APP_CONFIG_PUSH = "key.config.push";
	private static final String KEY_APP_CONFIG_QINIU = "key.config.qiniu";
	private static final String KEY_APP_CONFIG_UMENG = "key.config.umeng";
	private static final String KEY_APP_CONFIG_SHARE = "key.config.share";

//	public void setAppConfig(AppConfigBean config){
//
//		try {
//			if(config==null)
//				return ;
//			if(config.getPush()!=null){
//				saveSettings(KEY_APP_CONFIG_PUSH, JsonUtils.objToJson(config.getPush()));
//			}
//			if(config.getQiNiu()!=null){
//				saveSettings(KEY_APP_CONFIG_QINIU,JsonUtils.objToJson(config.getQiNiu()));
//			}
//			if(config.getUmeng()!=null){
//				saveSettings(KEY_APP_CONFIG_UMENG,JsonUtils.objToJson(config.getUmeng()));
//			}
//			if(config.getShare()!=null){
//				saveSettings(KEY_APP_CONFIG_SHARE,JsonUtils.objToJson(config.getShare()));
//			}
//		} catch (PrefenceException e) {
//			Logger.t(BuildConfig.ModuleName).e(e.getMessage());
//		}
//	}
//
//	public String getPushConfig(){
//		try {
//			return getSettingsString(KEY_APP_CONFIG_PUSH,"");
//		} catch (PrefenceException e) {
//			Logger.t(BuildConfig.ModuleName).e(e.getMessage());
//			return "";
//		}
//	}
//
//	public String getQiNiuConfig(){
//		try {
//			return getSettingsString(KEY_APP_CONFIG_QINIU,"");
//		} catch (PrefenceException e) {
//			Logger.t(BuildConfig.ModuleName).e(e.getMessage());
//			return "";
//		}
//	}
//
//	public String getShareConfig(){
//		try {
//			return getSettingsString(KEY_APP_CONFIG_SHARE,"");
//		} catch (PrefenceException e) {
//			Logger.t(BuildConfig.ModuleName).e(e.getMessage());
//			return "";
//		}
//	}
//
//	/********************  appconfig prefences end *********************/


}
