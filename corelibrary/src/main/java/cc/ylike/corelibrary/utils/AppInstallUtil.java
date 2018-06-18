package cc.ylike.corelibrary.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * app 自动更新安装工具类
 */
public class AppInstallUtil {


    public static void downLoad(Context context,String url,String fileName){
        PackageManager packageManager = context.getPackageManager();
        String archiveFilePath = getSystemFilePath(context) + File.separator + fileName + ".apk";
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(archiveFilePath , PackageManager.GET_ACTIVITIES);
        if (packageInfo == null) {

        }else {
            L.e(packageInfo.versionName);
            L.e(packageInfo.versionCode+"");
            L.e(packageInfo.packageName);
        }


    }

    private static String getSystemFilePath(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//            cachePath = context.getExternalCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看
        } else {
            cachePath = context.getFilesDir().getAbsolutePath();
//            cachePath = context.getCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看
        }

        L.e(cachePath);
        return cachePath;
    }

}
