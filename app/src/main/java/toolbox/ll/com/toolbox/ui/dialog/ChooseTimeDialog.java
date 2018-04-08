package toolbox.ll.com.toolbox.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import toolbox.ll.com.toolbox.R;

/**
 * Created by ll on 2018/4/8.
 */

public class ChooseTimeDialog extends Dialog {

    public ChooseTimeDialog(@NonNull Context context) {
        super(context);
    }

    public ChooseTimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ChooseTimeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_title);
        initView();
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
    private void initView(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_time,null);
        addContent(view);
    }
    private void addContent(View view){
        ViewGroup container = (ViewGroup) findViewById(R.id.layout_content);
        container.addView(view);
    }

}
