package toolbox.ll.com.toolbox.ui.base;

import android.os.Bundle;

/**
 * Created by Administrator on 2018/3/21.
 */

public interface BaseInit {
    public void beforeInit(Bundle savedInstanceState);
    public int getLayoutResId();
    public void initView();
    public void afterInit(Bundle savedInstanceState);
}
