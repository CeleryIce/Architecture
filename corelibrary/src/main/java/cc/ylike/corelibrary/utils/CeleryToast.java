package cc.ylike.corelibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cc.ylike.corelibrary.R;

/**
 * @author xsl
 * @version 1.0
 * @date 2017/4/11
 * @description
 * 自定义toast控制器
 */
public class CeleryToast {

    private static Toast toast;
    private static TextView messge;

    /**
     * short Toast
     * @param context 上下文对象
     * @param content 提示内容
     */
    public static void showShort(Context context,String content) {
        if (toast == null) {
            toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, DisplayUtils.dip2px(context,100));
            toast.setDuration(Toast.LENGTH_SHORT);
            View toastView = LayoutInflater.from(context).inflate(R.layout.celery_toast_view_layout,null);
            messge = (TextView)  toastView.findViewById(R.id.messge);
            messge.setText(content);
            toast.setView(toastView);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            messge.setText(content);
        }
        toast.show();
    }

    /**
     * long Toast
     * @param context 上下文对象
     * @param content 提示内容
     */
    public static void showLong(Context context,String content) {
        if (toast == null) {
            toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, DisplayUtils.dip2px(context,100));
            toast.setDuration(Toast.LENGTH_LONG);
            View toastView = LayoutInflater.from(context).inflate(R.layout.celery_toast_view_layout,null);
            messge = (TextView)  toastView.findViewById(R.id.messge);
            messge.setText(content);
            toast.setView(toastView);
        } else {
            toast.setDuration(Toast.LENGTH_LONG);
            messge.setText(content);
        }
        toast.show();
    }
}
