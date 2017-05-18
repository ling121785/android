package toolbox.ll.com.common.utility;

/**
 * Created by root on 2016/6/23.
 */
public class ActivityUtil {

//
//    public static void startSelectImgActivity(Activity activity, int limitSize) {
//        startSelectImgActivity(activity, 0, limitSize, SelectImgActivity.OPERATE_SELECT);
//    }
//
//    public static void startSelectImgActivity(Activity activity, int requestCode, int limitSize) {
//        startSelectImgActivity(activity, requestCode, limitSize, SelectImgActivity.OPERATE_SELECT);
//    }
//
//    //选择头像
//    public static void startSelectImgActivityForAvatar(Activity activity) {
//        startSelectImgActivity(activity, 0, 1, SelectImgActivity.OPERATE_AVATAR);
//    }
//
//    public static void startSelectImgActivity(Activity activity, int requestCode, int limitSize, int type) {
//        Intent intent = new Intent(activity, SelectImgActivity.class);
//        intent.putExtra(SelectImgActivity.LIMIT_COUNT, limitSize);
//        intent.putExtra(SelectImgActivity.OPERATE_TYPE, type);
//        activity.startActivityForResult(intent, requestCode);
//    }
//
//    public static Intent getSelectImgActivityIntent(Activity activity, int limitSize) {
//        Intent intent = new Intent(activity, SelectImgActivity.class);
//        intent.putExtra(SelectImgActivity.LIMIT_COUNT, limitSize);
//        return intent;
//    }
//
//    public static Intent getSelectImgActivityIntentForOne(Activity activity) {
//        Intent intent = new Intent(activity, SelectImgActivity.class);
//        intent.putExtra(SelectImgActivity.LIMIT_COUNT, 1);
//        intent.putExtra(SelectImgActivity.OPERATE_TYPE, SelectImgActivity.OPERATE_SINGLE);
//        return intent;
//    }
//
//    public static void startShowImgActivity(Context context, ArrayList<String> imgs) {
//        startShowImgActivity(context, imgs, 0);
//    }
//
//    public static void startShowImgActivity(Context context, ArrayList<String> imgs, int curItem) {
//        if (imgs != null && imgs.size() > 0) {
//            Intent intent = new Intent(context, ImageShowActivity.class);
//            intent.putExtra(ImageShowActivity.IMAGES, imgs);
//            intent.putExtra(ImageShowActivity.INDEX, curItem);
//            context.startActivity(intent);
//        }
//    }
//
//
//    public static void startCallActivity(Context context, String phoneNumber) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        } catch (Exception e) {
//            ToastUtils.showInForeground(context, R.string.life_yellowpage_call_activity_is_null);
//        }
//    }
//
//    public static void startBrowser(Context context, String url, String title) {
//        Intent intent = new Intent(context, BrowserActivity.class);
//        intent.putExtra(UIConstant.BROWSER_URL,url);
//        intent.putExtra(UIConstant.BROWSER_TITLE,title);
//        context.startActivity(intent);
//    }
//
//
//    public static Intent getInstalIntent(String appPath){
//        Intent install = new Intent();
//        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        install.setAction(android.content.Intent.ACTION_VIEW);
//        install.setDataAndType(Uri.fromFile(new File(appPath)),"application/vnd.android.package-archive");
//        return install;
//    }
//
//    public static void startApkUpdateActivity(Context context, VersionBean bean){
//        Intent intent=new Intent(context, ApkUpdateActivity.class);
//        intent.putExtra(UIConstant.VERSION_INFO,bean);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 创建桌面快捷方式
//     */
//   public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
//    public static final String ACTION_REMOVE_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";
//    public static final String ACTION_UNLOCK = "com.android.launcher.action.xzj.unlock";
//    public static void createShortCut(Context context, String name){
//        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
//
//        // 不允许重复创建
//        addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
//        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
//        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
//        // 屏幕上没有空间时会提示
//        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式
//
//        // 名字
//        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
//
//        // 图标
//        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//                Intent.ShortcutIconResource.fromContext(context,
//                        R.mipmap.ic_unlock_short_cut));
//
//        // 设置关联程序
//        Intent launcherIntent = new Intent(ACTION_UNLOCK);
//        launcherIntent.setClass(context, UnlockShortCutActivity.class);
//        launcherIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        launcherIntent.putExtra(UIConstant.SHORT_CUT_ACTION,name);
//
//        addShortcutIntent
//                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
//
//        // 发送广播
//        context.sendBroadcast(addShortcutIntent);
//    }
//
//
//    /**
//     * 移除桌面快捷方式
//     * @param name
//     */
//    private void removeShortcut(Context context, String name) {
//        // remove shortcut的方法在小米系统上不管用，在三星上可以移除
//        Intent intent = new Intent(ACTION_REMOVE_SHORTCUT);
//
//        // 名字
//        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
//
//        // 设置关联程序
//        Intent launcherIntent = new Intent(context,
//                LauncherActivity.class).setAction(Intent.ACTION_MAIN);
//
//        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
//
//        // 发送广播
//        context.sendBroadcast(intent);
//    }
}
