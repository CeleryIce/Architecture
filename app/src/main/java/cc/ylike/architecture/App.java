package cc.ylike.architecture;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import cc.ylike.corelibrary.CoreLibrary;
import cc.ylike.corelibrary.widgets.alertdialog.CeleryAlertDialogOptions;

/**
 * Created by xsl on 2018/5/4.
 */

public class App extends Application {

    @Override
    public void onCreate() {


        super.onCreate();

        //弹出框配置
        CeleryAlertDialogOptions dialogOptions = new CeleryAlertDialogOptions()
                .setInputLineColor(0xffe60000)
                .setPositiveButtonText("Sure")
                .setNegativeButtonText("Cancel")
                .setAlertDialogCenter(true);

        CoreLibrary.init(this,true)
                   .baseUrl(null)
                   .dialogOption(dialogOptions);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
