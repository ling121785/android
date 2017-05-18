package toolbox.ll.com.toolbox.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;

import java.io.ByteArrayOutputStream;

import toolbox.ll.com.common.utility.QiniuUtil;
import toolbox.ll.com.common.utility.StringUtils;
import toolbox.ll.com.toolbox.R;


/**
 * 图片加载工具
 * TODO 优化 ？
 */
public class ImageUtility {
    public static final int TYPE_PHOTO_SQUARE = 1;//照片广场
    public static final int TYPE_PHOTO_FAMILY = 2;//亲情相册
    public static final int TYPE_PHOTO_AVATAR = 3;//头像
    public static final int TYPE_ARRIVE = 4;//到家提醒
    public static final int TYPE_LIFE_MENU = 5;//主页菜单
    public static final int TYPE_MONITOR_LIST = 6;//监视列表
    public static final int TYPE_ALBUM = 7;//相册
    public static final int TYPE_SHOW_IMG = 8;//图片大图预览
    public static final int TYPE_HOUSE = 9;//小区
    public static final int TYPE_COMMENT = 10;//评论图片
    public static final int TYPE_LIFE_RECOMMEND = 11;//推荐商品图片
    public static final int TYPE_LIFE_AD = 12;//广告栏图片
    public static final int TYPE_REPAIR = 13;//报修
    public static final int TYPE_MONITO_CALL = 14;//来电


    private static DisplayImageOptions optionsPhotos = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new FadeInBitmapDisplayer(0))
            .build();
    private static DisplayImageOptions optionsAvatar = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.NONE_SAFE)
            .displayer(new FadeInBitmapDisplayer(0))
            .build();
    private static DisplayImageOptions optionsAblum = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(false)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.NONE_SAFE)
                    .displayer(new FadeInBitmapDisplayer(200))
//                    .displayer(new RoundedBitmapDisplayer(MainApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.general_round_def)))//是否设置为圆角，弧度为多少
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

    public static DisplayImageOptions DefaultOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .considerExifParams(true)
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .build();                                   // 创建配置过得DisplayImageOption对象


    public static DisplayImageOptions DefaultNotDefIconOptions = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
//            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
//            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .considerExifParams(true)
            .build();                                   // 创建配置过得DisplayImageOption对象

    public static DisplayImageOptions OptionsAppIcon = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .considerExifParams(true)
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡
            .build();                                   // 创建配置过得DisplayImageOption对象

    public static DisplayImageOptions OptionsHouseIcon = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡
            .build();                                   // 创建配置过得DisplayImageOption对象

    public static DisplayImageOptions MonitorOptions = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .considerExifParams(true)
            .build();                                   // 创建配置过得DisplayImageOption对象

    //初始化（配置参数）
    public static void init(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {//2.0
            int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
            if (memClass > 16) {
                memClass = 16;
            }
            memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheExtraOptions(720, 1080) // default = device screen dimensions:设备的屏幕尺寸
//                .memoryCache(new UsingFreqLimitedMemoryCache(memoryCacheSize * 3 / 8))//内存缓存
                .memoryCacheSizePercentage(10)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheExtraOptions(720, 1080, null)
                .diskCacheSize(50 * 1024 * 1024)//SDCARD缓存:50M
                .build();
        ImageLoader.getInstance().init(config);

        int imgSize=context.getResources().getDimensionPixelSize(R.dimen.general_local_img_def);
        ImageLoaderConfiguration loacalThrumbConfig = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheExtraOptions(imgSize, imgSize) // default = device screen dimensions:设备的屏幕尺寸
                .diskCacheExtraOptions(imgSize, imgSize, null)
                .diskCacheFileCount(100)
                .diskCacheSize(50* 1024 * 1024)//SDCARD缓存:50M
                .memoryCacheSizePercentage(10)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getLocalThrumbInstance().init(loacalThrumbConfig);
        L.writeLogs(true);
    }


    public static ImageLoadingListener mLoadingListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            if (view != null && view instanceof ImageView)
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    };

    public static ImageLoadingListener mLoadingListenerCenterCrop = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            if (view != null && view instanceof ImageView)
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    public static ImageLoadingListener mLoadingListenerSuccessCenterCrop = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(StringUtils.isEmpty(imageUri) ? ImageView.ScaleType.CENTER_INSIDE : ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            if (view == null)
                return;
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    };

    public static void displayImage(ImageView view, String url, int type) {
        switch (type) {
            case TYPE_PHOTO_SQUARE:
                ImageLoader.getInstance().displayImage(getThumbUrlM(url), view, optionsPhotos, mLoadingListenerCenterCrop);
                break;
            case TYPE_PHOTO_FAMILY:
                ImageLoader.getInstance().displayImage(getThumbUrl(url), view, optionsPhotos, mLoadingListenerCenterCrop);
                break;
            case TYPE_COMMENT:
                ImageAware imageAware = new ImageViewAware(view, false);
                ImageLoader.getInstance().displayImage(getThumbUrl(url), view, DefaultNotDefIconOptions, mLoadingListenerCenterCrop);
                break;
            case TYPE_ALBUM:
                ImageLoader.getLocalThrumbInstance().displayImage(getUrl(url), view, optionsAblum, mLoadingListenerSuccessCenterCrop);
                break;
            case TYPE_LIFE_MENU:
                ImageLoader.getInstance().displayImage(getUrl(url), view, DefaultNotDefIconOptions, mLoadingListener);
                break;
            case TYPE_PHOTO_AVATAR:
                ImageLoader.getInstance().displayImage(getThumbUrl(url), view, optionsAvatar, mLoadingListener);
                break;
            case TYPE_MONITOR_LIST:
                ImageLoader.getInstance().displayImage(getThumbUrl(url), view, MonitorOptions, mLoadingListenerSuccessCenterCrop);
                break;
            case TYPE_ARRIVE:
            case TYPE_REPAIR:
                ImageLoader.getInstance().displayImage(getThumbUrl(url), view, DefaultOptions, mLoadingListenerSuccessCenterCrop);
                break;
            case TYPE_HOUSE:
                ImageLoader.getInstance().displayImage(getThumbUrl(url), view, OptionsHouseIcon, mLoadingListener);
                break;
            case TYPE_LIFE_RECOMMEND:
                ImageLoader.getInstance().displayImage(getThumbUrl(url), view, DefaultOptions, mLoadingListenerSuccessCenterCrop);
                break;
            case TYPE_SHOW_IMG:
                ImageLoader.getInstance().displayImage(getThumbUrlM(url), view, DefaultNotDefIconOptions, mLoadingListener);
                break;
            case TYPE_LIFE_AD:
                ImageLoader.getInstance().displayImage(getThumbUrlM(url), view, DefaultOptions, mLoadingListenerSuccessCenterCrop);
                break;
            case TYPE_MONITO_CALL:
                ImageLoader.getInstance().displayImage(getUrl(url), view, optionsPhotos, mLoadingListenerCenterCrop);
                break;
            default:
                displayImage(view, url);
                break;
        }
    }

    public static String getUrl(String url) {
        if (url == null)
            return url;
        if (url.contains("http") || url.contains("www"))
            return url;
        return "file://" + url;
    }

    public static String getThumbUrl(String url) {
        if (url == null)
            return url;
        if (url.contains("http") || url.contains("www"))
            return QiniuUtil.getThumbnailUrl(url);
        return "file://" + url;
    }

    public static String getThumbUrlM(String url) {
        if (url == null)
            return url;
        if (url.contains("http") || url.contains("www"))
            return QiniuUtil.getThumbnailMediumUrl(url);
        return "file://" + url;
    }


    private static void displayImage(String url, ImageView view) {
        ImageLoader.getInstance().displayImage(url, view, DefaultOptions, mLoadingListener);
    }

    private static void displayImage(ImageView view, String url) {
        ImageLoader.getInstance().displayImage(url, view, DefaultOptions, mLoadingListener);
    }

    private static void displayImage(ImageView view, String url, DisplayImageOptions imageOptions) {
        ImageLoader.getInstance().displayImage(url, view, imageOptions, mLoadingListener);
    }


    public static byte[] bmpToByte(Bitmap bmp) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
        bmp.recycle();//自由选择是否进行回收
        byte[] result = output.toByteArray();//转换成功了
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
