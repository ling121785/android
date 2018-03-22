package toolbox.ll.com.toolbox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.roomBusiness.CreateRoomEvent;
import com.example.businessmodule.event.roomBusiness.JoinRoomEvent;
import com.example.businessmodule.utils.EventId;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.squareup.otto.Subscribe;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.account.LoginActivity;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tv_userName)
    TextView mTVUserName;

//    @BindView(R.id.main_sf_view)
//    AVChatSurfaceViewRenderer mSFView;
    @Override
    public void beforeInit(Bundle savedInstanceState) {
        BusinessInterface.getInstance().registerResponse(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
    }

    @OnClick(R.id.main_btn_login)
    public void gotoLogin(){
        this.startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.main_btn_joinRoom)
    public void joinRoom(){
        BusinessInterface.getInstance().request(new CreateRoomEvent(EventId.ROOM_CREATE,"room1","hahahh"));
    }

    @Subscribe
    public void CreateRoomResponse(CreateRoomEvent event){
        if(event.isSuccess()){
            ToastUtils.showToast(this,"房间创建成功");
            return;

        }
        ToastUtils.showToast(this,"房间创建失败");
        BusinessInterface.getInstance().request(new JoinRoomEvent(EventId.ROOM_JOIN,"room1", AVChatType.VIDEO));
    }

    @Subscribe
    public void joinRoomResponse(JoinRoomEvent event){
        if(event.isSuccess()){
            ToastUtils.showToast(this,"加入房间成功");
            return;

        }
        ToastUtils.showToast(this,"加入房间失败");
    }

    @Subscribe
    public void loginResponse(LoginEvent event){
        if(event.isSuccess()){
            mTVUserName.setText(event.response().getAccount());
            return;

        }
    }
}
