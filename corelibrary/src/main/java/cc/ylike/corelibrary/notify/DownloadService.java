package cc.ylike.corelibrary.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cc.ylike.corelibrary.R;
import cc.ylike.corelibrary.bus.EventBase;
import cc.ylike.corelibrary.bus.RxBus;
import cc.ylike.corelibrary.http.DownLoadProgressListener;
import cc.ylike.corelibrary.http.OkhttpClientUtils;
import cc.ylike.corelibrary.utils.CoreContants;
import cc.ylike.corelibrary.utils.L;
import cc.ylike.corelibrary.utils.ToolsUtils;

/**
 * 下载服务Service
 */
public class DownloadService extends Service {

    private NotificationManager manger;
    private int DOWNLOAD_TASK_MOST_COUNT = 5;
    private static Map<String,String> stringMap = new HashMap<>();
    private static Map<String,Integer> progressMap = new HashMap<>();


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        stringMap.clear();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent==null){
            return super.onStartCommand(intent, flags, startId);
        }
        String url = intent.getStringExtra(CoreContants.DOWNLOAD_URL);
        String saveFolder = intent.getStringExtra(CoreContants.DOWNLOAD_SAVE_FOlDER);
        boolean notity = intent.getBooleanExtra(CoreContants.DOWNLOAD_NOTITY,false);
        if (stringMap.size()< DOWNLOAD_TASK_MOST_COUNT) {
            if ((url.startsWith("http://") || url.startsWith("https://"))) {
                if (stringMap.get(url) == null) {
                    stringMap.put(url, saveFolder);
                    downLoad(url, notity);
                } else {
                    L.e("该文件正在下载，请勿重复添加");
                }
            } else {
                L.e("下载任务添加失败:下载地址无效" + url);
            }
        }else {
            L.e("下载任务添加失败:最多同时下载 " + DOWNLOAD_TASK_MOST_COUNT + " 个");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载任务
     * @param url 下载地址
     * @param notity 是否通知栏显示进度
     */
    private void downLoad(String url, boolean notity){
        new OkhttpClientUtils().downLoad(this, url, new DownLoadProgressListener() {
            @Override
            public void progress(float percent, boolean done,String tag) {
                int progress = (int) percent;
                //通知栏中显示
                if (done) {
                    if (notity) {
                        manger.cancel(tag, NotifyRetriever.TYPE_Progress);
                    }
                    progressMap.remove(tag);
                } else {
                    if (progressMap.get(tag) == null || progress > progressMap.get(tag)) {
                        if (notity) {
                            sendNotification(tag, progress);
                            progressMap.put(tag, progress);
                        }

                        /**
                         * 发送广播更新下载进度
                         * 控制发送速度防止内存溢出
                         * 待优化
                         */
                        ProgressInfo progressInfo = new ProgressInfo();
                        progressInfo.setPercent(percent);
                        progressInfo.setUrl(tag);
                        RxBus.getInstance().post(new EventBase(CoreContants.DOWNLOAD_PROGRESS_NOTITY, tag, progressInfo));

                    }
                }
            }

            @Override
            public void write(InputStream inputStream,String tag) {
                String saveFolder = stringMap.get(tag);
                //保存数据流到SD卡
                String[] string = tag.split("/");
                String savePath = ToolsUtils.savaFile(inputStream,saveFolder, string[string.length-1]);
                //发送广播更新保存进度
                ProgressInfo progressInfo = new ProgressInfo();
                progressInfo.setPercent(100);
                progressInfo.setUrl(tag);
                progressInfo.setFilePath(savePath);
                RxBus.getInstance().post(new EventBase(CoreContants.DOWNLOAD_PROGRESS_NOTITY,tag,progressInfo));
                stringMap.remove(tag);
            }

            @Override
            public void onFailure(int code, String Msg, Exception e,String tag) {
                try {
                    stringMap.remove(tag);
                    progressMap.remove(tag);
                }catch (Exception e1){
                    e1.printStackTrace();
                }
                L.e(Msg);
            }
        });
    }

    private void sendNotification(String tag, int progress){
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_3", "下载通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色;
            //是否在久按桌面图标时显示此渠道的通知
            channel.setShowBadge(true);
            //取消铃声
            channel.setSound(null,Notification.AUDIO_ATTRIBUTES_DEFAULT);
            //取消震动
            channel.setVibrationPattern(null);
            manger.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this,"channel_3");
        }else {
            builder = new NotificationCompat.Builder(this,"channel_3");
        }
        builder.setSmallIcon(R.drawable.notify_download);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notify_download));
        builder.build().sound = null;//取消铃声
        builder.build().vibrate = null;//取消震动
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        builder.setShowWhen(false);
        String[] string = tag.split("/");
        builder.setContentTitle(string[string.length-1] + "下载中..."+progress+"%");
        builder.setProgress(100,progress,false);
        Notification notification = builder.build();
        manger.notify(tag,NotifyRetriever.TYPE_Progress,notification);
    }

    @Override
    public void onDestroy() {
        manger.cancel(NotifyRetriever.TYPE_Progress);
        super.onDestroy();
    }
}
