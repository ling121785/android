package toolbox.ll.com.common.utility;

import java.text.DecimalFormat;

/**
 * Created by root on 2016/7/26.
 */
public class NumberUtils {
    public static String formatFloat(float value, String fromat){
        DecimalFormat decimalFormat =new DecimalFormat(fromat);//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String result = decimalFormat.format(value);//format 返回的是字符串
        return result;
    }

    public static String formatInt(int value, String fromat){
        DecimalFormat decimalFormat =new DecimalFormat(fromat);//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String result = decimalFormat.format(value);//format 返回的是字符串
        return result;
    }

    public static String formatFloatToMoney(float value){
        return formatFloat(value,"#.##");
    }

    public static String formatFloatToFullMoney(float value){
        return formatFloat(value,"0.00");
    }
}
