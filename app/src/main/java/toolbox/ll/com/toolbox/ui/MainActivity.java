package toolbox.ll.com.toolbox.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.widget.TextView;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.RoomBean;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.account.LogoutEvent;
import com.example.businessmodule.event.room.CreateRoomEvent;
import com.example.businessmodule.utils.EventId;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.common.widget.CircleImageView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.LiveStreamingBean;
import toolbox.ll.com.toolbox.bean.MainMenu;
import toolbox.ll.com.toolbox.ui.account.LoginActivity;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;
import toolbox.ll.com.toolbox.ui.live.LiveStreamingActivity;
import toolbox.ll.com.toolbox.utils.DialogUtil;
import toolbox.ll.com.toolbox.utils.ImageUtility;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tv_userName)
    TextView mTVUserName;

//    @BindView(R.id.main_lv_menu)
//    ListView mLVMenu;

    @BindView(R.id.main_iv_avatar)
    CircleImageView mIVAvatar;

    private List<MainMenu> mMenu;
    private  boolean mBPermission=false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;
    private NimUserInfo mUserInfo=null;
    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
//        mMenu=new ArrayList<>();
//        mMenu.add(new MainMenu("myGrade","我的等级",R.mipmap.ic_launcher));
//        mMenu.add(new MainMenu("fanContributions","粉丝贡献榜",R.mipmap.ic_launcher));
//        mMenu.add(new MainMenu("myLive","我的直播",R.mipmap.ic_launcher));
////        mMenu.add(new MainMenu(MainMenu.TYPE_PADDING));
//        mMenu.add(new MainMenu("setting","设置",R.mipmap.ic_launcher));
//        mLVMenu.setAdapter(new MainMenuAdapter(this,mMenu));
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        mBPermission = checkPublishPermission();
        AccountBean accountInfo= BusinessPrefences.getInstance().getUserInfo();
        if(accountInfo!=null){
            //自动登录
            BusinessInterface.getInstance().request(new LoginEvent(EventId.ACCOUNT_LOGIN,accountInfo.getUuid(),accountInfo.getPwd()));
        }
    }
    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mBPermission = true;
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.main_iv_avatar)
    public void gotoLogin(){
        if(mUserInfo==null){
            this.startActivity(new Intent(this, LoginActivity.class));
            return;
        }


    }

    @OnClick(R.id.main_tv_logout)
    public void logout(){
        BusinessInterface.getInstance().request(new LogoutEvent(EventId.ACCOUNT_LOGOT));
    }



    @OnClick(R.id.main_btn_joinRoom)
    public void joinRoom(){
        if(mUserInfo==null){
            this.startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        DialogUtil.showInputDialog(this, "直播间设置", "请输入直播间名字", new DialogUtil.DialogClickListener() {
            @Override
            public void comfirm(Object obj) {
                String [] posters={"http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/1.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/2.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/3.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/4.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/5.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/6.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/7.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/8.jpg",
                        "http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/9.jpg"};
                String poster=posters[new Random().nextInt(6)];
                BusinessInterface.getInstance().request(new CreateRoomEvent(EventId.ROOM_CREATE,(String)obj,"","http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/1.jpg"));
            }

            @Override
            public void cancel() {

            }
    });
    }

    /**
     * 进入房间
     * @param roomBean
     */
    public void gotoChatRoom(RoomBean roomBean){
        Intent intent=new Intent(MainActivity.this,LiveStreamingActivity.class);
        LiveStreamingBean liveStreamingBean=new LiveStreamingBean();
        liveStreamingBean.setRoomId(roomBean.getRoomId());
        liveStreamingBean.setPushUrl(roomBean.getLiveUrl());
        liveStreamingBean.setLiveId(roomBean.getLiveId());
        intent.putExtra("data",liveStreamingBean);
        startActivity(intent);
    }

    @Subscribe
    public void CreateRoomResponse(CreateRoomEvent event){
        if(event.isSuccess()){
            ToastUtils.showToast(this,"房间创建成功");
            gotoChatRoom(event.response());
            return;
        }
        ToastUtils.showToast(this,"房间创建失败");
//        BusinessInterface.getInstance().request(new JoinRoomEvent(EventId.ROOM_JOIN,"room1", AVChatType.VIDEO));
    }



    @Subscribe
    public void loginResponse(LoginEvent event){
        if(event.isSuccess()){
            this.mUserInfo= BusinessSession.getInstance().getUserInfo();
            mTVUserName.setText(mUserInfo.getName());
            ImageUtility.displayImage(mIVAvatar,mUserInfo.getAvatar(),ImageUtility.TYPE_PHOTO_AVATAR);
            return;

        }
    }

    @Subscribe
    public void logoutResponse(LogoutEvent event){
        if(event.isSuccess()){
            this.mUserInfo= BusinessSession.getInstance().getUserInfo();
            mTVUserName.setText("未登录");
            return;
        }
    }
}
