package toolbox.ll.com.toolbox.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import toolbox.ll.com.toolbox.ui.dialog.ChooseTimeDialog;
import toolbox.ll.com.toolbox.ui.dialog.ShareDialog;

/**
 * Created by Administrator on 2018/3/31.
 */

public class DialogUtil {

    public static  void showInputDialog(Context context, String title, String hintStr, final DialogClickListener clickListener) {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(context);
        editText.setHint(hintStr==null?"":hintStr);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(context);
        inputDialog.setTitle(title==null?"温馨提示":title).setView(editText);
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(clickListener==null)
                    return;
                clickListener.comfirm(editText.getText().toString());
            }
        }).show();
    }

    public static void showComfimDialog(Context context,String title,String msg,final DialogClickListener clickListener){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setTitle(title==null?"温馨提示":title);
        normalDialog.setMessage(msg==null?"":msg);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(clickListener==null)
                            return;
                        clickListener.comfirm();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(clickListener==null)
                            return;
                        clickListener.cancel();
                    }
                });
        // 显示
        normalDialog.show();
    }

    public static ChooseTimeDialog chooseTimeDialog(Context context,long startTime ,long endTime ,final DialogClickListener clickListener){
        ChooseTimeDialog dialog =  ChooseTimeDialog.chooseTimeDialog(context,startTime,endTime,clickListener);
        return dialog;
    }

    public static void showShareDialog(Context context,String msg){
        ShareDialog dialog=ShareDialog.showShareDialog(context,msg,null);
        dialog.show();
    }

    public interface DialogClickListener{
        public void comfirm(Object ... obj);
        public void cancel();
    }
}
