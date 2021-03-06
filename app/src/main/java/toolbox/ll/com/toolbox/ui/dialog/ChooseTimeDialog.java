package toolbox.ll.com.toolbox.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.DateUtils;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.utils.DialogUtil;

/**
 * Created by ll on 2018/4/8.
 */

public class ChooseTimeDialog extends Dialog implements  DatePicker.OnDateChangedListener{
    DialogUtil.DialogClickListener mListener;
    private long startTime=0;
    private long endTime=0;

    @BindView(R.id.dialog_et_startTime)
    EditText mETStartTime;
    @BindView(R.id.dialog_et_endTime)
    EditText mETEndTime;

    @BindView(R.id.base_tv_title)
    TextView mTVTitle;

    @BindView(R.id.base_tv_right)
    TextView mTVRight;
    
    @BindView(R.id.dialog_dataPicker)
    DatePicker mDatePicker;

    @BindView(R.id.dialog_btn_none)
    View mBtnNone;

    @BindView(R.id.dialog_btn_curMonth)
    View mBtnCurMonth;

    @BindView(R.id.dialog_btn_lastMonth)
    View mBtnLastMonth;

    @BindView(R.id.dialog_btn_curYear)
    View mBtnLastYear;

    @BindView(R.id.dialog_tv_during)
    TextView mTVDuring;

    private  long mCurEditID;

    private Calendar calendar=Calendar.getInstance();


    public ChooseTimeDialog(@NonNull Context context,long startTime,long endTime) {
        super(context);
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public ChooseTimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ChooseTimeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static ChooseTimeDialog chooseTimeDialog(Context context ,long startTime,long endTime ,final DialogUtil.DialogClickListener listener) {
        ChooseTimeDialog dialog = new ChooseTimeDialog(context, startTime, endTime);
        dialog.setmListener(listener);
        return dialog;
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
        ButterKnife.bind(this);
        afterInit();
    }



    private void initView(){
        ViewGroup container = (ViewGroup) findViewById(R.id.layout_content);
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_time,container,false);
        addContent(view);
    }
    private void addContent(View view){
        ViewGroup container = (ViewGroup) findViewById(R.id.layout_content);
        container.addView(view);
    }

    @SuppressLint("WrongConstant")
    private void afterInit() {
        mTVTitle.setText("筛选");
        mTVRight.setText("完成");
        Date date=new Date();
        if(startTime==0){
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            this.startTime=calendar.getTimeInMillis();
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            this.endTime=calendar.getTimeInMillis();
        }

        updateTime();
        resizeNumberPicker(mDatePicker);
        mDatePicker.init(date.getYear(),date.getMonth(),date.getDay(), this);
    }

    /*
       * 调整numberpicker大小
       */
    private void resizeNumberPicker(DatePicker np){
        View child = null;
        for(int i = 0;i<np.getChildCount();i++) {
            child = np.getChildAt(i);
            if (child instanceof NumberPicker) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                params.setMargins(10, 0, 10, 0);
                child.setLayoutParams(params);
            }
        }
    }
    private void updateTime(){
        if(this.startTime==-1){;
            mETStartTime.setVisibility(View.GONE);
            mTVDuring.setText("截止至");
            mETEndTime.setText(DateUtils.cutYearAndMonthAndDay(this.endTime));
            mBtnCurMonth.setSelected(false);
            mBtnLastMonth.setSelected(false);
            mBtnLastYear.setSelected(false);
            mBtnNone.setSelected(true);
        }else{
            mETStartTime.setVisibility(View.VISIBLE);
            mETStartTime.setText(DateUtils.cutYearAndMonthAndDay(this.startTime));
            mETEndTime.setText(DateUtils.cutYearAndMonthAndDay(this.endTime));
            mTVDuring.setText("至");
        }
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.dialog_et_startTime,R.id.dialog_et_endTime})
    public void showDatePicker(View view){
        this.mCurEditID=view.getId();
        mDatePicker.setVisibility(View.VISIBLE);
        calendar.setTimeInMillis(view.getId()==R.id.dialog_et_startTime?this.startTime:this.endTime);
        mDatePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));

    }

    @OnClick(R.id.base_ib_back)
    public void cancelClick(){
        if(mListener!=null)
            mListener.cancel();
    }

    @OnClick(R.id.base_tv_right)
    public void confirmClick(){
        if(mListener!=null)
            mListener.comfirm(this.startTime,this.endTime);
        this.hide();
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.dialog_btn_none,R.id.dialog_btn_curMonth,R.id.dialog_btn_lastMonth,R.id.dialog_btn_curYear})
    public void chooseTimeClick(View view){
        mBtnCurMonth.setSelected(false);
        mBtnLastMonth.setSelected(false);
        mBtnLastYear.setSelected(false);
        mBtnNone.setSelected(false);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        this.endTime=calendar.getTimeInMillis()-1;
        switch (view.getId()){
            case R.id.dialog_btn_none:
                calendar.add(Calendar.DAY_OF_MONTH,-7);
                this.startTime=-1;
                mBtnNone.setSelected(true);
                break;
            case R.id.dialog_btn_curMonth:
                calendar.set(Calendar.DAY_OF_MONTH,1);
                this.startTime=calendar.getTimeInMillis();
                mBtnCurMonth.setSelected(true);
                break;
            case R.id.dialog_btn_lastMonth:
                calendar.set(Calendar.DAY_OF_MONTH,1);
                this.endTime=calendar.getTimeInMillis()-1;
                calendar.add(Calendar.MONTH,-1);
                this.startTime=calendar.getTimeInMillis();
                mBtnLastMonth.setSelected(true);
                break;
            case R.id.dialog_btn_curYear:
                calendar.set(Calendar.DAY_OF_YEAR,1);
                this.startTime=calendar.getTimeInMillis();
                mBtnLastYear.setSelected(true);
                break;
        }
       updateTime();


    }


    public DialogUtil.DialogClickListener getmListener() {
        return mListener;
    }

    public void setmListener(DialogUtil.DialogClickListener mListener) {
        this.mListener = mListener;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        mBtnCurMonth.setSelected(false);
        mBtnLastMonth.setSelected(false);
        mBtnLastYear.setSelected(false);
        mBtnNone.setSelected(false);
        if(this.mCurEditID==R.id.dialog_et_startTime){
            this.startTime=calendar.getTimeInMillis();
            mETStartTime.setText(DateUtils.cutYearAndMonthAndDay(this.startTime));
        }else{
            this.endTime=calendar.getTimeInMillis();
            mETEndTime.setText(DateUtils.cutYearAndMonthAndDay(this.endTime));
        }
    }
}
