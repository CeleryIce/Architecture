package cc.ylike.corelibrary.widgets.alertdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

import cc.ylike.corelibrary.R;
import cc.ylike.corelibrary.utils.CeleryToast;
import cc.ylike.corelibrary.utils.L;
import cc.ylike.corelibrary.utils.PictureFromSysUtil;
import cc.ylike.corelibrary.utils.ToolsUtils;
import retrofit2.http.Url;


/**
 * Created by Celery on 2016/10/25.
 * @version 1.0
 * @author xsl
 * @des 使用方法
 *
 */

/**
 * onActivityResult使用方法示例
 */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK){
//            switch (requestCode){
//                case PictureFromSysUtil.SELECT_PICTURES:
//                    Uri album = PictureFromSysUtil.albumOnActivityResult(mContext, data);
//                    PictureFromSysUtil.startPhotoZoom(mContext,album);
//                    break;
//                case PictureFromSysUtil.TAKE_PHOTO:
//                    Uri carmera = PictureFromSysUtil.carmeraOnActivityResult(mContext, data);
//                    PictureFromSysUtil.startPhotoZoom(mContext,carmera);
//                    break;
//                case PictureFromSysUtil.CROP_PICTURE:
//                    Bitmap bitmap  = BitmapFactory.decodeFile(PictureFromSysUtil.outPutUri.getPath());
//                    imageView.setImageBitmap(bitmap);
//                    break;
//            }
//        }
//    }
public class AvatarDialog {

    /**
     * 弹出框
     * @param context
     */
    public static void show(Activity context){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.avatar_dialog, null);
        final Dialog dialog = new Dialog(context,R.style.bottomDialog);
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle);

        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.picture) {
                    PictureFromSysUtil.OpenAlbum(context);
                    dialog.dismiss();
                } else if (i == R.id.takephoto) {
                    PictureFromSysUtil.OpenCarmera(context);
                    dialog.dismiss();
                } else if (i == R.id.cancel) {
                    dialog.dismiss();
                }
            }
        };

        dialogView.findViewById(R.id.picture).setOnClickListener(onclick);
        dialogView.findViewById(R.id.takephoto).setOnClickListener(onclick);
        dialogView.findViewById(R.id.cancel).setOnClickListener(onclick);
        dialog.show();
    }




}
