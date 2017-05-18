package toolbox.ll.com.common.utility;

import android.widget.TextView;

/**
 * Created by root on 2017/5/18.
 */

public class ViewHelper {
    public static void fillView(TextView textView, String str){
        fillView(textView,str,"");
    }

    public static void fillView(TextView textView,String...strList){
        if(textView==null)
            return;
        textView.setText("");
        if(strList==null)
            return ;
        for(String str:strList){
            if(StringUtils.isEmpty(str)){
                continue;
            }else{
                textView.setText(str);
                break;
            }


        }
    }
}
