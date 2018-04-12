package toolbox.ll.com.toolbox.ui.live;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.widget.CircleImageView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;

public class LiveEndActivity extends BaseActivity {

  @BindView(R.id.liveEnd_iv_avatar)
    CircleImageView mIVAvatar;

  @BindView(R.id.liveEnd_tv_duration)
    TextView mTVDuration;

    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_live_end;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }

    @OnClick(R.id.liveEnd_btn_exit)
    public void exit(){
        this.finish();
    }
}
