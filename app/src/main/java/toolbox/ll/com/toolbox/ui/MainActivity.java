package toolbox.ll.com.toolbox.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.businessmodule.bean.UserInfo;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.roomBusiness.CreateRoomEvent;
import com.example.businessmodule.event.roomBusiness.JoinRoomEvent;
import com.example.businessmodule.utils.EventId;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.squareup.otto.Subscribe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.common.widget.CircleImageView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.MainMenu;
import toolbox.ll.com.toolbox.ui.account.LoginActivity;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;

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
    private LoginInfo mUserInfo=null;
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
        UserInfo accountInfo= BusinessPrefences.getInstance().getUserInfo();
        if(accountInfo!=null){
            //自动登录
            BusinessInterface.getInstance().request(new LoginEvent(EventId.ACCOUNT_LOGIN,accountInfo.getAccount(),accountInfo.getPwd()));
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

    @OnClick(R.id.main_btn_joinRoom)
    public void joinRoom(){
        if(mUserInfo==null){
            this.startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        startActivity(new Intent(this,LiveStreamingActivity.class));
    }

    @Subscribe
    public void CreateRoomResponse(CreateRoomEvent event){
        if(event.isSuccess()){
            ToastUtils.showToast(this,"房间创建成功");
            return;

        }
        ToastUtils.showToast(this,"房间创建失败");
//        BusinessInterface.getInstance().request(new JoinRoomEvent(EventId.ROOM_JOIN,"room1", AVChatType.VIDEO));
    }



    @Subscribe
    public void loginResponse(LoginEvent event){
        if(event.isSuccess()){
            this.mUserInfo=event.response();
            mTVUserName.setText(event.response().getAccount());
            return;

        }
    }
}
