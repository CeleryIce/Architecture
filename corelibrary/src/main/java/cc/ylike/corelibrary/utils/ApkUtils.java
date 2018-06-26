package cc.ylike.corelibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import java.io.File;
import cc.ylike.corelibrary.notify.DownloadService;
import cc.ylike.corelibrary.utils.CoreContants;
import cc.ylike.corelibrary.utils.ToolsUtils;

/**
 * apk 自动更新安装工具类
 */
public class ApkUtils {

    /**
     * apk下载
     * @param context
     * @param url
     */
    public static void downLoad(Context context,String url){
        String archiveFilePath = ToolsUtils.getSystemFilePath(context,Environment.DIRECTORY_DOWNLOADS) + File.separator + ToolsUtils.getAppName(context) + ".apk";
        if (hasLocalLasterApk(context)){
            //跳转安装app
            ToolsUtils.installApk(context,new File(archiveFilePath));
        }else {
            //说明本地没有现成的安装包，启动下载
            Intent intent = new Intent(context, DownloadService.class);
            intent.putExtra(CoreContants.DOWNLOAD_URL,url);
            intent.putExtra(CoreContants.DOWNLOAD_SAVE_FOlDER,ToolsUtils.getSystemFilePath(context,Environment.DIRECTORY_DOWNLOADS));
            intent.putExtra(CoreContants.DOWNLOAD_SAVE_FILE_NAME,ToolsUtils.getAppName(context)+".apk");
            intent.putExtra(CoreContants.DOWNLOAD_NOTITY,true);
            context.startService(intent);
        }
    }

    /**
     * 检查本地是否有最新版apk安装包
     * @param context
     * @return
     */
    public static boolean hasLocalLasterApk(Context context){
        PackageManager packageManager = context.getPackageManager();
        String archiveFilePath = ToolsUtils.getSystemFilePath(context,Environment.DIRECTORY_DOWNLOADS) + File.separator + ToolsUtils.getAppName(context) + ".apk";
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(archiveFilePath , PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            try {
                int index = ToolsUtils.compareVersion(packageInfo.versionName,ToolsUtils.getVersionName(context));
                if (index>=0){
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }






}
