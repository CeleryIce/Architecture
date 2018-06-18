package cc.ylike.architecture;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.ylike.architecture.di.component.DaggerActivityComponent;
import cc.ylike.architecture.di.module.ActivityModule;
import cc.ylike.architecture.mvp.contract.MainActivityContract;
import cc.ylike.architecture.mvp.presenter.MainActivityPresenter;
import cc.ylike.corelibrary.bus.EventBase;
import cc.ylike.corelibrary.bus.RxBus;
import cc.ylike.corelibrary.bus.RxBusEvent;
import cc.ylike.corelibrary.notify.DownloadService;
import cc.ylike.corelibrary.notify.ProgressInfo;
import cc.ylike.corelibrary.utils.CoreContants;
import cc.ylike.corelibrary.utils.L;


public class MainActivity extends BaseActivity implements MainActivityContract.View{

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Inject
    MainActivityPresenter activityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this,this)).build().inject(this);
        setSwipeBackEnable(false);
        RxBus.getInstance().register(this);
    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:

//                Intent intent1 = new Intent(MainActivity.this,SecondActivity.class);
//                NotifyComponent.with(mContext,true)
//                        .setContentTitle("测试通知")
//                        .setContentText(System.currentTimeMillis()+"")
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setLargeIcon(R.mipmap.ic_launcher)
//                        .setIntent(intent1)
//                        .setNotifyTag(true)
//                        .showinBoxStyleNotity("bigtxt","bigtittle","sumtext");

//                ImageLoader.with(this)
//                        .load("https://www.baidu.com/img/baidu_jgylogo3.gif")
//                        .into(imageView);
                new RxPermissions(MainActivity.this).request(Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            Intent intent = new Intent(mContext, DownloadService.class);
                            intent.putExtra(CoreContants.DOWNLOAD_URL,"https://celery-master.oss-cn-shenzhen.aliyuncs.com/201805021643.apk");
                            intent.putExtra(CoreContants.DOWNLOAD_SAVE_FOlDER,"corelibrary");
                            intent.putExtra(CoreContants.DOWNLOAD_NOTITY,false);
                            startService(intent);
                        } else {
                            // Oups permission denied
                            L.e("动态请求权限失败");
                        }
                    });
                break;
            case R.id.button2:
//                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
//                NotifyComponent.with(mContext,false)
//                        .setTicker("悬浮消息")
//                        .setContentTitle("测试通知")
//                        .setContentText(System.currentTimeMillis()+"")
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setLargeIcon(R.mipmap.ic_launcher)
//                        .setIntent(intent)
//                        .showSimpleNotity("小字");

                new RxPermissions(MainActivity.this).request(Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                Intent intent = new Intent(mContext, DownloadService.class);
                                intent.putExtra(CoreContants.DOWNLOAD_URL,"https://celery-master.oss-cn-shenzhen.aliyuncs.com/201805251137.apk");
                                intent.putExtra(CoreContants.DOWNLOAD_SAVE_FOlDER,"corelibrary");
                                intent.putExtra(CoreContants.DOWNLOAD_NOTITY,true);
                                startService(intent);
                            } else {
                                // Oups permission denied
                                L.e("动态请求权限失败");
                            }
                        });

//                activityPresenter.getData();
//                startActivity(intent);
                break;
        }
    }


    /**
     * 接收下载更新信息
     * @param eventBase
     */
    @RxBusEvent(value = CoreContants.DOWNLOAD_PROGRESS_NOTITY)
    public void onEvent(EventBase eventBase) {
        ProgressInfo info = (ProgressInfo) eventBase.getData();
        if (info.getPercent() == 100){
            L.e("下载完成：" + info.getUrl());
            L.e("文件路径：" + info.getFilePath());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }
}
