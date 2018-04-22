package toolbox.ll.com.toolbox.ui.live;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.LiveStyleBean;
import com.example.businessmodule.bean.RoomBean;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.room.CreateRoomEvent;
import com.example.businessmodule.event.room.LiveStyleListEvent;
import com.example.businessmodule.utils.EventId;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.LiveStreamingBean;
import toolbox.ll.com.toolbox.ui.MainActivity;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;
import toolbox.ll.com.toolbox.utils.ImageUtility;

public class StartLiveActivity extends BaseActivity {
    @BindView(R.id.startLive_et_room_charge)
    EditText mEtRoomCharge;
    @BindView(R.id.startLive_et_pwd)
    EditText mEtRoomPwd;
    @BindView(R.id.startLive_et_roomName)
    EditText mEtRoomName;
    @BindView(R.id.startLive_et_roomStyle)
    Spinner mSPRoomStyle;
    @BindView(R.id.startLive_et_num)
    EditText mEtRoomNum;

    @BindView(R.id.startLive_cb_room_charge)
    CheckBox mCBRoomCharge;

    @BindView(R.id.startLive_cb_room_cencryption)
    CheckBox mCBRoomLock;

    @BindView(R.id.act_bg)
    View mViewBg;

    @BindView(R.id.startLive_iv_cover)
    ImageView mIVCovor;

    @BindView(R.id.startLive_layout_room_encryption)
    ViewGroup mLayoutRoomEncryption;
    private static final int PHOTO_REQUEST_CAREMA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private File tempFile;
    private File mPostFile;

    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_start_live;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        File file = new File(this.getFilesDir(), "_head_icon.jpg");
        if (file.exists()) {
            mIVCovor.setImageURI(Uri.fromFile(file));
            mPostFile=file;
        }
        AccountBean mUserInfo= BusinessSession.getInstance().getAccountInfo();
        if(mUserInfo!=null){

        }
        BusinessInterface.getInstance().request(new LiveStyleListEvent(EventId.ROOM_STYLE_LIST));
    }

    @Subscribe
    public void liveStyleEvent(LiveStyleListEvent event){
        if(event.isSuccess()) {
            mSPRoomStyle.setAdapter(new StartLiveStyleAdapter(this,event.response().getDataList()));
            if(event.response().getDataList().size()>0){
                mSPRoomStyle.setSelection(0);
            }
            mSPRoomStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mSPRoomStyle.setTag(adapterView.getItemAtPosition(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @OnClick(R.id.startLive_tv_cover)
    public void choseImg(){
        startChooseImg();
    }

    @OnClick(R.id.startLive_iv_close)
    public void close(){
        this.finish();
    }

    @OnClick(R.id.startLive_btn_start)
    public void startLive(){
        String roomName=mEtRoomName.getText().toString();
//        String poster="http://onmxkx5tf.bkt.clouddn.com/zhibo/poster/1.jpg";
        Integer style=mSPRoomStyle.getTag()!=null&&mSPRoomStyle.getTag()instanceof LiveStyleBean ?((LiveStyleBean)mSPRoomStyle.getTag()).getId():null;
        Integer charge=null;
        if(mCBRoomCharge.isChecked()){
            if(mEtRoomCharge.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,"请设置收费金额");
                return;
            }
            charge=Integer.parseInt(mEtRoomCharge.getText().toString().trim());
        }
        String pwd=null;
        if(mCBRoomLock.isChecked()){
            if(mEtRoomPwd.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,"请设置房间密码");
                return;
            }
            if(mEtRoomPwd.getText().toString().trim().length()!=6){
                ToastUtils.showToast(this,"房间密码未6位数");
                return;
            }
            pwd=mEtRoomPwd.getText().toString().trim();
        }
        Integer maxNum=mEtRoomNum.getText().toString().trim().equals("")?null:Integer.parseInt(mEtRoomNum.getText().toString().trim());
        BusinessInterface.getInstance().request(new CreateRoomEvent(EventId.ROOM_CREATE,roomName,"",mPostFile,style,charge,pwd,maxNum));
    }

    @OnCheckedChanged(R.id.startLive_cb_room_charge)
    public void roomChargeChange(boolean checked){
        mEtRoomCharge.setVisibility(checked? View.VISIBLE:View.GONE);
    }

    @OnCheckedChanged(R.id.startLive_cb_room_cencryption)
    public void roomcEncryption(boolean checked){
        mLayoutRoomEncryption.setVisibility(checked? View.VISIBLE:View.GONE);
    }

    private void startChooseImg(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,
                PHOTO_REQUEST_GALLERY);
    }

    private void openCamenra(){
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            tempFile = new File(Environment
                    .getExternalStorageDirectory(),
                    PHOTO_FILE_NAME);
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent,
                    PHOTO_REQUEST_CAREMA);
        } else {
            Toast.makeText(this, "未找到存储卡，无法存储照片！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                Logger.e("图片路径？？", data.getData() + "");
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(this, "未找到存储卡，无法存储照片！",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                final Bitmap bitmap = data.getParcelableExtra("data");
                mIVCovor.setImageBitmap(bitmap);
                // 保存图片到internal storage
                FileOutputStream outputStream;
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    // out.close();
                    // final byte[] buffer = out.toByteArray();
                    // outputStream.write(buffer);
                    outputStream = this.openFileOutput("_head_icon.jpg",
                            Context.MODE_PRIVATE);
                    out.writeTo(outputStream);
                    out.close();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File file = new File(this.getFilesDir(), "_head_icon.jpg");
                if (file.exists()) {
                    mPostFile=file;
                }
            }
            try {
                if (tempFile != null && tempFile.exists())
                    tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Subscribe
    public void createRoomResponse(CreateRoomEvent event){
        if(event.isSuccess()){
            ToastUtils.showToast(this,"房间创建成功");
            gotoChatRoom(event.response());
            return;
        }
        ToastUtils.showToast(this,"房间创建失败");
//        BusinessInterface.getInstance().request(new JoinRoomEvent(EventId.ROOM_JOIN,"room1", AVChatType.VIDEO));
    }

    /**
     * 进入房间
     * @param roomBean
     */
    public void gotoChatRoom(RoomBean roomBean){
        Intent intent=new Intent(this,LiveStreamingActivity.class);
        LiveStreamingBean liveStreamingBean=new LiveStreamingBean();
        liveStreamingBean.setRoomId(roomBean.getRoomId());
        liveStreamingBean.setPushUrl(roomBean.getLiveUrl());
        liveStreamingBean.setLiveId(roomBean.getLiveId());
        intent.putExtra("data",liveStreamingBean);
        startActivity(intent);
        this.finish();
    }

}
