package toolbox.ll.com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import toolbox.ll.com.toolbox.R;

/**
 * 
 * com.evideo.weiju.ui.widget.RoundProgressBar
 * @version V1.0.0
 * @author Fiona Chen <br/>
 * Create at  2015-3-11 下午4:11:40
 */
public class VerticalProgressBar extends View {
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;
	
	/**
	 * 圆环的颜色
	 */
	private int progressColor;
	
	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColor;
	
	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSize;
	
	/**
	 * 最大进度
	 */
	private int max;
	
	/**
	 * 当前进度
	 */
	private int progress;
	/**
	 * 是否显示中间的进度
	 */
	private boolean textIsDisplayable;
	
	public VerticalProgressBar(Context context) {
		this(context, null);
	}

	public VerticalProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public VerticalProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		paint = new Paint();

		
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.VerticalProgressBar);
		
		//获取自定义属性和默认值
		progressColor = mTypedArray.getColor(R.styleable.VerticalProgressBar_progressColor, Color.RED);
		textColor = mTypedArray.getColor(R.styleable.VerticalProgressBar_textColor, Color.GREEN);
		textSize = mTypedArray.getDimension(R.styleable.VerticalProgressBar_textSize, 15);
		max = mTypedArray.getInteger(R.styleable.VerticalProgressBar_maxLength, 100);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.VerticalProgressBar_textIsDisplayable, true);
		progress = -1;
		mTypedArray.recycle();
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		paint.setColor(progressColor); //设置圆环的颜色
		paint.setStyle(Paint.Style.FILL); //设置空心
		paint.setAntiAlias(true);  //消除锯齿 
		int height = getHeight() * (100 - progress) / 100;
		
		if(progress != -1)
			canvas.drawRect(0, 0, getWidth(), height, paint);
		
		
		/**
		 * 画进度百分比
		 */
		int centreX = getWidth()/2; //获取中心的x坐标
		int centreY = getHeight()/2; //获取中心的x坐标
		paint.setStrokeWidth(0); 
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
		int percent = (int)(((float)progress / (float)max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
		float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
		
		if(textIsDisplayable && percent != 0 && percent != -1){
			canvas.drawText(percent + "%", centreX - textWidth / 2, centreY + textSize/2, paint); //画出进度百分比
		} else if(textIsDisplayable == false) {
			
		}
		
	}
	
	
	public synchronized int getMax() {
		return max;
	}

	/**
	 * 设置进度的最大值
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * 获取进度.需要同步
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
	 * 刷新界面调用postInvalidate()能在非UI线程刷新
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress > max){
			progress = max;
		}
		if(progress <= max){
			this.progress = progress;
			postInvalidate();
		}
		
	}
	
	
	public int getProgressColor() {
		return progressColor;
	}

	public void setProgressColor(int progressColor) {
		this.progressColor = progressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public boolean isTextIsDisplayable() {
		return textIsDisplayable;
	}

	public void setTextIsDisplayable(boolean textIsDisplayable) {
		this.textIsDisplayable = textIsDisplayable;
	}
	
	

}
