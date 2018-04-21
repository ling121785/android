package toolbox.ll.com.toolbox.ui.live;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.event.room.LiveDetailEvent;
import com.example.businessmodule.utils.EventId;
import com.squareup.otto.Subscribe;

import org.w3c.dom.Text;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.widget.CircleImageView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;
import toolbox.ll.com.toolbox.utils.ImageUtility;

public class LiveEndActivity extends BaseActivity {

  @BindView(R.id.liveEnd_iv_avatar)
  CircleImageView mIVAvatar;

  @BindView(R.id.liveEnd_tv_duration)
  TextView mTVDuration;

  @BindView(R.id.liveEnd_tv_likeNum)
  TextView mTVLikeNum;

  @BindView(R.id.liveEnd_tv_newFansNum)
  TextView mTVNewFansNum;

  @BindView(R.id.liveEnd_tv_coin)
  TextView mTVCoin;

  @BindView(R.id.liveEnd_tv_watchNum)
  TextView mTVWatchNum;

   private  String liveId;

    @Override
    public void beforeInit(Bundle savedInstanceState) {
      liveId=getIntent().getStringExtra("data");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_live_end;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
      BusinessInterface.getInstance().request(new LiveDetailEvent(EventId.ROOM_END_DETAIL,liveId));
    }

    @Subscribe
    public void liveDetailEvent(LiveDetailEvent event){
      if(event.getEventId()!=EventId.ROOM_END_DETAIL)
        return;
      if(event.isSuccess()){
        ImageUtility.displayImage(mIVAvatar,event.response().getIcon(),ImageUtility.TYPE_PHOTO_AVATAR);
        mTVLikeNum.setText(event.response().getLikeNum()+"");
        mTVCoin.setText(event.response().getCoin()+"");
        mTVWatchNum.setText(event.response().getTotalWatchNum()+"");
        mTVNewFansNum.setText(event.response().getNewFansNum()+"");
      }

    }

    @OnClick(R.id.liveEnd_btn_exit)
    public void exit(){
        this.finish();
    }
}
