package toolbox.ll.com.toolbox.ui.live;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;

public class StartLiveActivity extends BaseActivity {
    @BindView(R.id.startLive_et_room_charge)
    EditText mEtRoomCharge;
    @BindView(R.id.startLive_et_pwd)
    EditText mEtRoomPwd;
    @BindView(R.id.startLive_et_roomName)
    EditText mEtRoomName;
    @BindView(R.id.startLive_et_roomStyle)
    EditText mEtRoomStyle;
    @BindView(R.id.startLive_et_num)
    EditText mEtRoomNum;

    @BindView(R.id.startLive_layout_room_encryption)
    ViewGroup mLayoutRoomEncryption;


    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_start_live;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }

    @OnClick(R.id.startLive_tv_cover)
    public void choseImg(){

    }

    @OnClick(R.id.startLive_iv_close)
    public void close(){
        this.finish();
    }
    @OnClick(R.id.startLive_btn_start)
    public void startLive(){

    }

    @OnCheckedChanged(R.id.startLive_cb_room_charge)
    public void roomChargeChange(boolean checked){
        mEtRoomCharge.setVisibility(checked? View.VISIBLE:View.GONE);
    }

    @OnCheckedChanged(R.id.startLive_cb_room_charge)
    public void roomcEncryption(boolean checked){
        mLayoutRoomEncryption.setVisibility(checked? View.VISIBLE:View.GONE);
    }

}
