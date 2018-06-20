package cc.ylike.architecture;

import android.app.Application;

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
    }
}
