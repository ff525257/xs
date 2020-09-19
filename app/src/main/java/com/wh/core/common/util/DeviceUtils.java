package com.wh.core.common.util;

import java.io.File;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


/**
 * 类名: DeviceUtils <br/>
 * 功能描述: 获取手机信息. <br/>
 * @author fmh
 */
public class DeviceUtils {

    public static int displayWidth = 0;
    public static int displayHeight = 0;
    public static float displaydensity = 0;
    public static int displayDpi = 0;


    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }

    /**
     * 返回屏幕分辨率的宽度  px
     *
     * @param act
     * @return
     */
    public static int getDisplayWidth(Context act) {
        if (displayWidth == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) act).getWindowManager().getDefaultDisplay().getMetrics(dm);
            displayWidth = dm.widthPixels;
        }
        return displayWidth;
    }

    public static int getDensityDpi(Context act) {
        if (displayDpi == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) act).getWindowManager().getDefaultDisplay().getMetrics(dm);
            displayDpi = dm.densityDpi;
        }
        return displayDpi;
    }

    public static float getDisplaydensity(Context act) {
        if (displaydensity == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) act).getWindowManager().getDefaultDisplay().getMetrics(dm);
            displaydensity = dm.density;
        }
        return displaydensity;
    }

    /**
     * 返回屏幕分辨率的高度  px
     *
     * @param act
     * @return
     */
    public static int getDisplayHeight(Context act) {
        if (displayHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) act).getWindowManager().getDefaultDisplay().getMetrics(dm);
            displayHeight = dm.heightPixels;
        }
        return displayHeight;
    }


    /**
     * dip2px:根据手机的分辨率从 dp(像素) 的单位 转成为 px. <br/>
     *
     * @param dpValue dp值
     * @param context
     * @return px值
     * @author fmh
     * @since 1.0
     */
    public static int dip2px(float dpValue, Context context) {
        if (displaydensity == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            displaydensity = dm.density;
        }
        return (int) (dpValue * displaydensity + 0.5f);
    }

    /**
     * px2dip:根据手机的分辨率从 px(像素) 的单位 转成为 dp. <br/>
     *
     * @param pxValue px值
     * @param context
     * @return dp值
     * @author fmh
     * @since 1.0
     */
    public static int px2dip(float pxValue, Context context) {
        if (displaydensity == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            displaydensity = dm.density;
        }
        return (int) (pxValue / displaydensity + 0.5f);
    }

    /**
     * getIMEI:获取IMEI号. <br/>
     *
     * @param context
     * @return
     * @author fmh
     * @since 1.0
     */
    public static String getIMEI(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String deviceId = telManager.getDeviceId();  //手机上的IMEI号
        if (deviceId == null || "".equals(deviceId)) {
            return "";
        } else {
            return deviceId;
        }
    }

    /**
     * haveInternet:判断当前网络状态是否可用. <br/>
     *
     * @param context
     * @return true:可用 false:不可用
     * @author fmh
     * @since 1.0
     */
    public static boolean haveInternet(Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return false;
        }
        //漫游状态
        if (info.isRoaming()) {
            return true;
        }
        return true;
    }

    /**
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 安装应用,主要是自升级使用
     *
     * @param context
     * @param path
     */
    public static void installApk(Context context, String path) {
        Intent intentInstall = new Intent();
        intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentInstall.setAction(Intent.ACTION_VIEW);
        intentInstall.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        context.startActivity(intentInstall);
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(String phoneNum, Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }


    public static synchronized String getProperty(Context context, String str, String str2) {
        synchronized (DeviceUtils.class) {
            String str3;
            if (str == null || str2 == null) {
                str3 = "";
                return str3;
            } else if (context == null) {
                return str2;
            } else {
                try {
                    if (Build.VERSION.SDK_INT >= 24) {
                        Context createDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
                        if (PreferenceManager.getDefaultSharedPreferences(createDeviceProtectedStorageContext).getString("moveSharedPreferencesFrom", "N").equals("N")) {
                            createDeviceProtectedStorageContext.moveSharedPreferencesFrom(context, PreferenceManager.getDefaultSharedPreferencesName(context));
                            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(createDeviceProtectedStorageContext).edit();
                            edit.putString("moveSharedPreferencesFrom", "Y");
                            edit.commit();
                        }
                        context = createDeviceProtectedStorageContext;
                    }
                    str3 = PreferenceManager.getDefaultSharedPreferences(context).getString(str, str2);
                    return str3;
                } catch (Exception e) {
                    return str2;
                }
            }
        }
    }

    public static String getVersionName(Context context, String pkg) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(pkg, 0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 跳转到相册
     */
    public static void gotoPhoto(Activity activity, int request, String title) {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(intent, title), request);
    }

    /**
     * 跳转到照相机
     */
    public static void gotoCamera(Activity activity, int request, String authority,File tempFile) {
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, authority, tempFile);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        activity.startActivityForResult(intent, request);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

}
