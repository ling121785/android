package com.example.baseui.base.core;


import android.support.v7.app.AppCompatActivity;

public class AnimationActivity extends AppCompatActivity {
	public static final String TAG = AnimationActivity.class.getCanonicalName();
	
	public static final boolean OpenAnimation = true;
//	public static final int OpenEnter = R.anim.a1;
//	public static final int OpenExit = R.anim.a2;
//	public static final int CloseEnter = R.anim.a3;
//	public static final int CloseExit = R.anim.a4;
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//当当前的activity只有一个的时候，点击推送的时候，启动luncher
		if(ActivityManager.getInstance().getStackCount()<2){
//			Intent intent=new Intent(this, LauncherActivity.class);
//			startActivity(intent);
		}
//		if(AnimationActivity.OpenAnimation)
//			overridePendingTransition(AnimationActivity.CloseEnter, AnimationActivity.CloseExit);

	}

}
