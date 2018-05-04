package cc.ylike.corelibrary.widgets.alertdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cc.ylike.corelibrary.R;


/**
 * Created by Celery on 2016/10/25.
 * @version 1.0
 * @author xsl
 * @des
 * https://github.com/Celery1025
 * superzilin1025@gmail.com
 */
public class AvatarDialog {

    public static final int SELECT_PICTURES = 0x0111;
    public static final int TAKE_PHOTO = 0x0112;
    public static final int CROP_PICTURE = 0x0113;

    public AvatarDialog(final Activity context, View dropView, final Uri imageUri){
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
                Intent intent = null;
                int i = v.getId();
                if (i == R.id.picture) {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    context.startActivityForResult(intent, SELECT_PICTURES);
                    dialog.dismiss();
                } else if (i == R.id.takephoto) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    context.startActivityForResult(intent, TAKE_PHOTO);
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
