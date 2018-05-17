package cc.ylike.corelibrary.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.io.File;
import java.io.InputStream;

import cc.ylike.corelibrary.http.DownLoadProgressListener;
import cc.ylike.corelibrary.http.OkhttpClientUtils;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by xsl on 2018/1/15.
 * 通知栏下载
 */
public class NotificationTask {

    private static final int TYPE_Progress = 2;
    private int progress = 0;
    private NotificationManager manger;
    private NotificationCompat.Builder builder;
    private Bitmap bitmap_largeIcon;
    private int oldProgress = 0;

    /**
     * 下载app覆盖安装升级
     * @param context Activity
     * @param url 下载地址
     * @param largeIcon 通知栏大图标
     * @param smallIcon 状态栏小图标
     * @param saveFolder 要保存的文件夹 例如：corelibrary 或者 corelibrary/file 支持多级
     */
    public synchronized void downLoadApp(Activity context,String url,int largeIcon,int smallIcon,String saveFolder){
        bitmap_largeIcon = BitmapFactory.decodeResource(context.getResources(),largeIcon);
        manger = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //适配安卓8.0的消息渠道
        String channel_id = System.currentTimeMillis()+"";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, "channel_1",NotificationManager.IMPORTANCE_HIGH);
            //取消铃声
            channel.setSound(null,Notification.AUDIO_ATTRIBUTES_DEFAULT);
            //取消震动
            channel.setVibrationPattern(null);
            manger.createNotificationChannel(channel);
        }
        builder = new NotificationCompat.Builder(context, channel_id);
        builder.build().sound = null;//取消铃声
        builder.build().vibrate = null;//取消震动
        builder.setSmallIcon(smallIcon);
        builder.setLargeIcon(bitmap_largeIcon);
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        builder.setShowWhen(true);

        if (!(url.startsWith("http://") || url.startsWith("https://"))){
            CeleryToast.showShort(context,"安装包下载失败");
            return;
        }

        new OkhttpClientUtils().downLoad(context, url, new DownLoadProgressListener() {

            @Override
            public void progress(float percent, boolean done) {
                progress = (int) percent;
                if(progress>99){
                    progress=0;
                    manger.cancel(TYPE_Progress);
                }else{
                    if (progress>oldProgress) {
                        builder.setContentTitle("下载中..."+progress+"%");
                        builder.setProgress(100,progress,false);
                        Notification notification = builder.build();
                        manger.notify(TYPE_Progress,notification);
                        oldProgress = progress;
                    }
                }
            }


            @Override
            public void write(InputStream inputStream) {
                //保存数据流到SD卡
                String savePath = CeleryToolsUtils.savaFile(inputStream
                        ,saveFolder
                        , System.currentTimeMillis()+".apk");

                //跳转安装app
                CeleryToolsUtils.installApp(context,new File(savePath),0);

            }

            @Override
            public void onFailure(int code, String Msg, Exception e) {
                L.e(Msg);
            }
        });
    }





}
