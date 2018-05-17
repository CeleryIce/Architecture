package cc.ylike.architecture.mvp.presenter;

import android.app.Activity;

import java.util.HashMap;

import javax.inject.Inject;

import cc.ylike.architecture.bean.LoginBean;
import cc.ylike.architecture.http.ApiService;
import cc.ylike.architecture.http.HttpUtils;
import cc.ylike.architecture.mvp.contract.MainActivityContract;
import cc.ylike.corelibrary.mvp.BaseIView;
import cc.ylike.corelibrary.mvp.biz.BasePresenterImpl;
import cc.ylike.corelibrary.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xsl on 2018/5/17.
 */

public class MainActivityPresenter extends BasePresenterImpl implements MainActivityContract.Presenter {


    private MainActivityContract.View view;
    private ApiService apiService = HttpUtils.getService(retrofit);

    @Inject
    public MainActivityPresenter(Activity activity,BaseIView baseIView) {
        super(activity);
        view = (MainActivityContract.View) baseIView;
    }


    @Override
    public void getData() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("mobile","13127574526");
        map.put("password","123456");
        Call<LoginBean> call = apiService.doLogin(map);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                L.e(t.getMessage());
            }
        });
    }
}
