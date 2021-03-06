package toolbox.ll.com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by root on 2016/6/2.
 */
public class ListViewNotScroll extends ListView {

    public ListViewNotScroll(Context context) {
        super(context);
    }

    public ListViewNotScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewNotScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}