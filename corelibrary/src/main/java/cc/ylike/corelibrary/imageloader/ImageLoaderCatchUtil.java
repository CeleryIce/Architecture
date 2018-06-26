package cc.ylike.corelibrary.imageloader;

import android.os.Looper;
import com.bumptech.glide.Glide;
import java.io.File;
import cc.ylike.corelibrary.CoreLibrary;


public class ImageLoaderCatchUtil {

    private static ImageLoaderCatchUtil instance;

    public static ImageLoaderCatchUtil getInstance() {
        if (null == instance) {
            instance = new ImageLoaderCatchUtil();
        }
        return instance;
    }

    /**
     * 获取Glide磁盘缓存大小
     * @return
     */
    public long getCacheSize() {
        try {
            File file = Glide.getPhotoCacheDir(CoreLibrary.AtContext);
            return file.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     *  清除图片磁盘缓存，调用Glide自带方法
     * @return
     */
    public boolean clearCacheDisk() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(CoreLibrary.AtContext).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(CoreLibrary.AtContext).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
