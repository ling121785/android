package toolbox.ll.com.toolbox.ui.setting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import toolbox.ll.com.toolbox.ui.base.BaseTitleActivity;
import toolbox.ll.com.toolbox.ui.live.LiveStreamingActivity;
import toolbox.ll.com.toolbox.ui.user.MyFansActivity;
import toolbox.ll.com.toolbox.ui.user.MyIncomeActivity;
import toolbox.ll.com.toolbox.ui.user.MyLiveActivity;
import toolbox.ll.com.toolbox.utils.DialogUtil;
import toolbox.ll.com.toolbox.utils.ImageUtility;

public class SettingActivity extends BaseTitleActivity {
    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }


    @Override
    public void initView() {
        super.initView();
        addContent(R.layout.activity_setting);
    }

    @OnClick({R.id.setting_layout_about,R.id.setting_layout_cache,
            R.id.setting_layout_feedback,R.id.setting_layout_msgSetting,
    R.id.setting_layout_updatePwd,R.id.setting_layout_version})
    public void menuClick(View v){
        switch (v.getId()){
            case R.id.setting_layout_about:
                break;
            case R.id.setting_layout_cache:
                break;
            case R.id.setting_layout_feedback:
                break;
            case R.id.setting_layout_msgSetting:
                break;
            case R.id.setting_layout_updatePwd:
                break;
            case R.id.setting_layout_version:
                break;
        }
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        setTitle("设置");
    }

    @OnClick(R.id.setting_btn_logout)
    public void logout(){
        BusinessInterface.getInstance().request(new LogoutEvent(EventId.ACCOUNT_LOGOT));
    }

    @Subscribe
    public void logoutResponse(LogoutEvent event){
        this.finish();
    }
}
