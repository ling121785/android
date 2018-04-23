package toolbox.ll.com.toolbox.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.businessmodule.bean.GiftAnimationBean;
import com.example.businessmodule.bean.GiftBean;
import com.example.businessmodule.core.BusinessSession;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class GiftUtil {
    public static List<Drawable> split(Bitmap bitmap, GiftAnimationBean bean) {
        if(bitmap==null||bean==null||bean.getList()==null||bean.getList().size()==0)
            return null;
        List<Drawable> pieces = new ArrayList<Drawable>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int i = 0; i < bean.getList().size(); i++) {
            GiftAnimationBean.PieceBean pieceInfo=bean.getList().get(i);
            if(pieceInfo==null)
                continue;
            int x=pieceInfo.getX()>0?pieceInfo.getX():-pieceInfo.getX();
            int y= pieceInfo.getY()>0? pieceInfo.getY():- pieceInfo.getY();
            Bitmap item = Bitmap.createBitmap(bitmap, x, y,
                    pieceInfo.getWidth(), pieceInfo.getHeight());
                pieces.add(new BitmapDrawable(item));
        }
        return pieces;
    }

    public static AnimationDrawable showGiftAnimation(Bitmap bitmap, GiftAnimationBean bean){
        AnimationDrawable anination=new AnimationDrawable();
        List<Drawable> list=split(bitmap,bean);
        if(list==null)
            return null;
        int during=(int)bean.getSeconds()*1000/list.size();
        for (int i=0;i<list.size();i++){
            anination.addFrame(list.get(i),during);
        }
        return anination;

    }
    public static GiftBean getGiftBean(int id){
        List<GiftBean> list= BusinessSession.getInstance().getmGiftList();
        if(list==null)
            return null;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getId()==id)
                return list.get(i);
        }
        return null;
    }
}
