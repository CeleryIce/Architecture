package cc.ylike.architecture.http;




import java.util.Map;

import cc.ylike.architecture.bean.LoginBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Zhou on 2017/4/6.
 * 统一接口定义，采用retrofit2，配合Rxjava 实现调用。
 */
public interface ApiService {






    //登录接口
    @POST("https://apps.e-lingcloud.com/app/login.ihtml")
    Call<LoginBean> doLogin(@Body Map<String,Object> map);



}
