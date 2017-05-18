package toolbox.ll.com.common.widget;

 import android.content.Context;
 import android.support.annotation.Nullable;
 import android.util.AttributeSet;
import android.view.View;
 import android.support.v7.widget.RecyclerView;
/**
 * Created by root on 2016/6/2.
 */
public class RecycleViewNotScroll extends RecyclerView {

    public RecycleViewNotScroll(Context context) {
        super(context);
    }

    public RecycleViewNotScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleViewNotScroll(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}