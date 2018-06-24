package cc.ylike.corelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

import cc.ylike.corelibrary.CoreLibrary;

/**
 * 从系统中获取图片工具类
 * 兼容4.4 以及android N 以上版本
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
public class PictureFromSysUtil {

    public static final int SELECT_PICTURES = 0x0111;
    public static final int TAKE_PHOTO = 0x0112;
    public static final int CROP_PICTURE = 0x0113;

    private static File mCameraFile = new File(ToolsUtils.getSystemFilePath(CoreLibrary.AtContext,Environment.DIRECTORY_PICTURES), "camera.jpg");//照相机的File对象
    //裁剪后的File对象
    private static File mCropFile = new File(ToolsUtils.getSystemFilePath(CoreLibrary.AtContext,Environment.DIRECTORY_PICTURES), "camera_crop.jpg");
    public static Uri outPutUri = Uri.fromFile(mCropFile);

    /**
     * 打开相机拍照
     */
    public static void OpenCarmera(Activity context) {
        if (ToolsUtils.hasSdcard()) {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", mCameraFile);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
            }
            context.startActivityForResult(intentFromCapture, TAKE_PHOTO);
        } else {
            CeleryToast.showShort(context, "未找到存储卡，无法存储照片！");
        }
    }


    /**
     * 打开相册
     */
    public static void OpenAlbum(Activity context) {
        File mGalleryFile = new File(ToolsUtils.getSystemFilePath(context,Environment.DIRECTORY_PICTURES), "gallery.jpg");//照相机的File对象
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", mGalleryFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        context.startActivityForResult(intent, SELECT_PICTURES);
    }


    /**
     * 相册选择单张图片后onActivityResult 返回uri
     * @param context
     * @param data
     * @return
     */
    public static Uri albumOnActivityResult(Context context, Intent data) {
        Uri dataUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File imgUri = new File(ImageTool.getImageAbsolutePath(context, data.getData()));
            dataUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", imgUri);
        } else {
            dataUri = data.getData();
        }
        return dataUri;
    }

    public static Uri carmeraOnActivityResult(Context context, Intent data) {
        Uri inputUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            inputUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", mCameraFile);
        } else {
             inputUri = Uri.fromFile(mCameraFile);
        }
        return inputUri;
    }


    /**
     * 裁剪图片方法实现
     * @param context
     * @param inputUri
     */
    public static void startPhotoZoom(Activity context, Uri inputUri) {
        if (inputUri == null) {
            L.i("The uri is not exist.");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            //去除默认的人脸识别，否则和剪裁匡重叠
            intent.putExtra("noFaceDetection", false);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                //这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                String url = ImageTool.getImageAbsolutePath(context, inputUri);
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        // 图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //这里就将裁剪后的图片的Uri返回了
        context.startActivityForResult(intent, CROP_PICTURE);
    }


}
