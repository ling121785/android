package toolbox.ll.com.toolbox.ui.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.toolbox.R;

/**
 * Created by ll on 2018/5/2.
 */

public class ShareDialog extends Dialog {
    private String mMsg;
    private Object mTag;

    private Unbinder mBinder;

    @BindView(R.id.dialog_tv_msg)
    TextView mTViewMsg;

    @BindView(R.id.dialog_iv_qrcode)
    ImageView mIVQRCode;

    public static ShareDialog showShareDialog(Context context, String msg, Object tag) {
        ShareDialog dialog = new ShareDialog(context, msg, tag);
        return dialog;
    }

    public ShareDialog(Context context, String msg, Object tag) {
        super(context, R.style.ConfirmDialog);
        mMsg = msg;
        mTag = tag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        View rootView = getLayoutInflater().inflate(R.layout.dialog_share, null);
        setContentView(rootView);
        mBinder= ButterKnife.bind(this);
        mTViewMsg.setText("地址："+mMsg);
        Bitmap mBitmap = CodeUtils.createImage(mMsg, 200,200, null);
        mIVQRCode.setImageBitmap(mBitmap);

    }

    @OnClick(R.id.dialog_btn_copy)
    public void copy(){
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

// 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText(null, this.mMsg);
// 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
        ToastUtils.showToast(getContext(),"复制成功");
        dismiss();
    }


    @Override
    public void dismiss() {
        mBinder.unbind();
        super.dismiss();
    }
}
