package cc.ylike.corelibrary.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import cc.ylike.corelibrary.notify.DownloadService;

/**
 * app 自动更新安装工具类
 */
public class AppUtils {


    /**
     * app 下载安装
     * @param context
     * @param url
     */
    public static void install(Context context,String url){
        PackageManager packageManager = context.getPackageManager();
        String archiveFilePath = getSystemFilePath(context) + File.separator + ToolsUtils.getAppName(context) + ".apk";
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(archiveFilePath , PackageManager.GET_ACTIVITIES);
        if (packageInfo == null) {
            //说明本地没有现成的安装包，启动下载
            Intent intent = new Intent(context, DownloadService.class);
            intent.putExtra(CoreContants.DOWNLOAD_URL,url);
            intent.putExtra(CoreContants.DOWNLOAD_SAVE_FOlDER,getSystemFilePath(context));
            intent.putExtra(CoreContants.DOWNLOAD_NOTITY,true);
            context.startService(intent);
        }else {
            try {
                int index = ToolsUtils.compareVersion(packageInfo.versionName,ToolsUtils.getVersionName(context));
                if (index>0){
                    //跳转安装app
                     

                }else {
                    //本地版本过低，继续下载新版本
                    Intent intent = new Intent(context, DownloadService.class);
                    intent.putExtra(CoreContants.DOWNLOAD_URL,url);
                    intent.putExtra(CoreContants.DOWNLOAD_SAVE_FOlDER,getSystemFilePath(context));
                    intent.putExtra(CoreContants.DOWNLOAD_NOTITY,true);
                    context.startService(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * app系统文件下载路径
     * 这个路径保存的数据会随着apk卸载一起清除
     * @param context
     * @return
     */
    public static String getSystemFilePath(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//            cachePath = context.getExternalCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看
        } else {
            cachePath = context.getFilesDir().getAbsolutePath();
//            cachePath = context.getCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看
        }
        return cachePath;
    }




}
