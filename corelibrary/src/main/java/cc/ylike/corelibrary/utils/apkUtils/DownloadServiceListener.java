package cc.ylike.corelibrary.utils.apkUtils;

import java.io.InputStream;
import java.io.Serializable;

public interface DownloadServiceListener extends Serializable{

    /**
     * 下载进度监听
     * @param percent 下载进度百分比
     * @param tag 下载路径标记
     */
    void progress(float percent,String tag);

    /**
     * 下载成功监听
     * @param filePath 文件保存绝对路径
     * @param tag 下载路径标记
     */
    void success(String filePath,String tag);

    /**
     * 下载失败
     * @param e 异常
     * @param tag 下载路径标记
     */
    void onFailure(Exception e,String tag);
}
