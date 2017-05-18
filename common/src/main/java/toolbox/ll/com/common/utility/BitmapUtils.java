package toolbox.ll.com.common.utility;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {


    public static void getExifInfo(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            float[] f = new float[2];
            exifInterface.getLatLong(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean saveBitmapToJpegFile(Bitmap bitmap, String filePath,
                                               int quality, int maxWidth, int maxHeight) {
        FileOutputStream fileOutStr = null;
        BufferedOutputStream bufOutStr = null;
        try {
            fileOutStr = new FileOutputStream(filePath);
            bufOutStr = new BufferedOutputStream(
                    fileOutStr);
            Bitmap bm = resizeBitmap(bitmap, maxWidth, maxHeight);
            bm.compress(CompressFormat.JPEG, quality, bufOutStr);
            bufOutStr.flush();
            bufOutStr.close();
        } catch (Exception exception) {
            return false;
        } finally {

            try {
                if (fileOutStr != null)
                    fileOutStr.close();
                if (bufOutStr != null)
                    bufOutStr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    /**
     * 缩小图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width >= height
                    && width > maxWidth) {
                int pWidth = maxWidth;
                int pHeight = maxWidth * height / width;
                Bitmap result = Bitmap.createScaledBitmap(bitmap, pWidth, pHeight, false);
                bitmap.recycle();
                return result;
            }
            if (width < height
                    && height > maxHeight) {
                int pHeight = maxHeight;
                int pWidth = maxHeight * width / height;
                Bitmap result = Bitmap.createScaledBitmap(bitmap, pWidth, pHeight, false);
                bitmap.recycle();
                return result;
            }
        }
        return bitmap;
    }

    /**
     * 获取图片文件的信息，是否旋转了90度，如果是则反转
     *
     * @param bitmap 需要旋转的图片
     * @param path   图片的路径
     */
    public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
        int degree = getPicRotate(path);
        if (degree != 0) {
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 读取图片文件旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    public static final int UploadThumbnailMaxWidth = 1024;
    public static final int UploadThumbnailMaxHeight = 1024;
    public static final int UploadThumbnailQuality = 50;

    public static boolean getThumbImag(String imgPath, String saveFilePath) {
        FileInputStream inputStream = null;
        try {
            File imgFile = new File(imgPath);
            inputStream = new FileInputStream(imgFile);
            File saveFile = new File(saveFilePath);
            if (!saveFile.getParentFile().exists())
                saveFile.getParentFile().mkdirs();
            int size = (int) Math.ceil(imgFile.length() / (800 * 1024));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = size;
            //将图片缩小为原来的  1/size ,不然图片很大时会报内存溢出错误
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            bitmap = reviewPicRotate(bitmap, imgPath);
            inputStream.close();
            return saveBitmapToJpegFile(bitmap, saveFilePath, UploadThumbnailQuality
                    , UploadThumbnailMaxWidth, UploadThumbnailMaxHeight);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
