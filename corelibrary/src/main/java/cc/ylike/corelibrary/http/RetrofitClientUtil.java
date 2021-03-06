package cc.ylike.corelibrary.http;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cc.ylike.corelibrary.CoreLibrary;
import cc.ylike.corelibrary.utils.CoreLibraryRetriever;
import cc.ylike.corelibrary.utils.L;
import cc.ylike.corelibrary.utils.NetworkUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xieshuilin on 2017/2/16.
 * @version 1.0
 * @author xsl
 * @des 通过Rxjava 和 retrofit2 结合封装 http请求
 * 实现如下功能
 * 1、请求参数（get、post表单、post Json字符串）打印
 * 2、实现json数据解析
 * 3、动态添加请求头
 * 4、动态替换baseurl
 * 5、日志打印窗口实现json数据自动解析显示
 * 6、单例模式实现对象获取
 * 7、等等整合。
 */
public class RetrofitClientUtil {

    protected static BaseApiService service = null;
    protected static  Retrofit retrofit = null;
    private static OkHttpClient  client = null;


    /**
     * 初始化Retrofit
     */
    public static Retrofit getRetrofit(Context context){
        if (service == null){
            //获取实例
            retrofit = new Retrofit.Builder()
                    //设置OKHttpClient,如果不设置会提供一个默认的
                    .client(getClient(context))
                    //设置baseUrl
                    .baseUrl(CoreLibraryRetriever.baseUrl)//post 方法
                    //添加Gson转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }


    /**
     * log日志拦截
     */
    private static class LoggingInterceptor implements Interceptor {

        private Context mContext;
        public LoggingInterceptor(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();

            long t1 = System.nanoTime();//请求发起的时间

//            String sendLog = String.format("发送请求 %s on %s%n%s", request.url(), chain.connection(), request.headers());

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            String content = responseBody.string();
            String reciveLog = String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n",
                    URLDecoderForUtf8(response.request().url().toString()),
                    content,
                    (t2 - t1) / 1e6d);

            /**
             * 回调拦截的body（用于全局拦截）
             */
            if (CoreLibrary.responseBodyInterceptorListener != null){
                CoreLibrary.responseBodyInterceptorListener.responseBody(response.request().url().toString(),response.headers().toString(),content);
            }

            L.d(reciveLog);
            L.d("响应时间："+((t2 - t1)/1e6d)+"");
            L.json(content);

            if (NetworkUtil.isNetworkAvailable(mContext)) {
                int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }

            return response;
        }
    }

    /**
     * 请求拦截器，修改请求header
     */
    private static class RequestInterceptor implements Interceptor{

        private Context mContext;
        CacheControl controlCache = null;
        public RequestInterceptor(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
                if (NetworkUtil.isNetworkAvailable(mContext)) {
                     controlCache = CacheControl.FORCE_NETWORK;
                }else {
                    controlCache = CacheControl.FORCE_CACHE;
                }

            Request.Builder builder = chain.request()
                    .newBuilder();
            builder.cacheControl(controlCache);

            //添加请求头
            Map<String, String> maps = CoreLibrary.getHeaders();
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                builder.header(entry.getKey(),entry.getValue());
            }

            Request request = builder.build();
            L.d(URLDecoderForUtf8(request.toString()));
            //post请求打印请求参数
            if(request.method().equals("POST")){
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append( "\n" + body.encodedName(i) + "=" + URLDecoderForUtf8(body.encodedValue(i)));
                    }
                    L.d("表单提交请求参数：\n"+ "{\n" +  sb.toString() + "\n}");
                }else {
                    //buffer流
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    String oldParamsJson = buffer.readUtf8();
                    L.d("Json传递请求参数："+ URLDecoderForUtf8(oldParamsJson));
                }
            }

            L.d("headers：" + request.headers().toString());
            return chain.proceed(request);
        }
    }

    /**
     * URLEncoder 编码转换
     * @param str 要解码成中文的字符串
     * @return
     */
    private static String URLDecoderForUtf8(String str){
        try {
            return java.net.URLDecoder.decode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static OkHttpClient getClient(Context mContext){
           if (client == null) {
            ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
            File cacheFile = new File(mContext.getApplicationContext().getCacheDir(), "celery_retrofit");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //100Mb
            client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor(mContext.getApplicationContext()))
                    .addInterceptor(new RequestInterceptor(mContext.getApplicationContext()))
//                    .cookieJar(cookieJar)
                    .cache(cache)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
       return client;
    }




    /**
     * 组装字符串数据
     * @param paramsMap
     * @return
     */
    public static RequestBody getRequestBody(HashMap<String,String> paramsMap){
        StringBuilder jsonStr = new StringBuilder("");
        for (String key : paramsMap.keySet()) {
            jsonStr.append(key).append("=").append(paramsMap.get(key)).append("&");
        }
        jsonStr.delete(jsonStr.length()-1,jsonStr.length());
        System.out.print(jsonStr.toString());
        RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonStr.toString());
        return body;
    }


}
