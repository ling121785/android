package toolbox.ll.com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SquareImageViewWithRound extends RoundAngleImageView {
	public SquareImageViewWithRound(Context paramContext) {
		super(paramContext);
	}

	public SquareImageViewWithRound(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		int i = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
				View.MeasureSpec.EXACTLY);
		super.onMeasure(i, i);
	}
}