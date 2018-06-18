package cc.ylike.corelibrary;

import android.app.Application;


import java.util.HashMap;
import java.util.Map;

import cc.ylike.corelibrary.http.onResponseBodyInterceptorListener;
import cc.ylike.corelibrary.utils.CoreLibraryRetriever;

/**
 * @author xsl
 * @version 1.0
 * @date 2017/4/11
 * @description corelibrary 初始化内容
 *
 * @author xsl
 * @version 2.0
 * @date 2018/5/3
 * @description 优化代码、去除无用功能
 */
public class CoreLibrary {

    public static Application AtContext;

    /**
     * 请求头map
     */
    private static Map<String, String> MapHeaders;


    /**
     * 请求响应body拦截
     */
    public static onResponseBodyInterceptorListener responseBodyInterceptorListener;

    /**
     * 初始化
     * @param context Application
     * @param isDebug 是否是调试模式
     * @return
     */
    public static CoreLibraryRetriever init(Application context, boolean isDebug) {
        AtContext = context;
        CoreLibraryRetriever requestListRetriever = CoreLibraryRetriever.get();
        return requestListRetriever.init(context,isDebug);
    }

    /**
     * 初始化请求头，建议在登录后初始化
     */
    public static void initHeaders(Map<String,String> headerMap){
        MapHeaders = headerMap;
    }

    /**
     * 获取请求头
     * @return
     */
    public static Map<String,String> getHeaders(){
        if (MapHeaders != null) {
            return MapHeaders;
        }
        return new HashMap<String,String>();
    }

    /**
     * 请求响应数据拦截监听
     */
    public static void setResponseBodyInterceptor(onResponseBodyInterceptorListener onResponseBodyInterceptorListener){
        responseBodyInterceptorListener = onResponseBodyInterceptorListener;
    }


}
