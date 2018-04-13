package toolbox.ll.com.toolbox.ui.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.toolbox.R;

import static android.view.ViewGroup.LayoutParams.*;

public abstract  class BaseTitleActivity extends BaseActivity {
    @BindView(R.id.base_ib_back)
    View mBtnBack;
    @BindView(R.id.base_tv_title)
    TextView mTVTitle;
    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_title;
    }
    public void addContent(View view){
        ViewGroup container = (ViewGroup) findViewById(R.id.layout_content);
        container.addView(view);
    }

    public void addContent(int resId){
        View view= LayoutInflater.from(this).inflate(resId,null);
        addContent(view);
    }
    @Override
    public void initView() {
        super.initView();
    }

    public void setTitled(int resId){
        mTVTitle.setText(resId);
    }
    public void setTitle(String title){
        mTVTitle.setText(title);
    }

    public void hideBack(){
        mBtnBack.setVisibility(View.GONE);
    }

    @OnClick(R.id.base_ib_back)
    public void backClick(){
        this.finish();
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}
