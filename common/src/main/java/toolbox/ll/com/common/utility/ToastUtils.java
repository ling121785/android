package toolbox.ll.com.common.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import toolbox.ll.com.common.R;

public class ToastUtils {
    private  static Toast mToast;
    private static TextView messageTextView;
    private static View toastRootView;

    public static void showToast(Context context, int resID) {
        showCustomToast(context, resID);
    }

    public static void showToast(Context context, String msg) {
        showCustomToast(context, msg);
    }

    public static void showInForeground(Context context, int resID) {
        if (!DeviceUtils.isApplicationBroughtToBackground(context))
            showCustomToast(context, resID);
    }

    public static void showInForeground(Context context, String msg) {
        if (!DeviceUtils.isApplicationBroughtToBackground(context))
            showCustomToast(context, msg);
    }

    private static void showCustomToast(Context context, Object message) {
        if (mToast == null) {
            toastRootView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
            if (toastRootView == null)
                return;

            mToast = new Toast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(toastRootView);
        }
        messageTextView = (TextView)toastRootView.findViewById(R.id.messageTextView);
        if (message instanceof String)
            messageTextView.setText((String) message);
        else if (message instanceof Integer)
            messageTextView.setText((Integer) message);
        else
            return;
        mToast.show();
    }

}
