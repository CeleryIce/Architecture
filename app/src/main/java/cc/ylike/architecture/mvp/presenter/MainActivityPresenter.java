package cc.ylike.architecture.mvp.presenter;

import android.app.Activity;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;

import javax.inject.Inject;

import cc.ylike.architecture.bean.LoginBean;
import cc.ylike.architecture.http.ApiService;
import cc.ylike.architecture.http.HttpUtils;
import cc.ylike.architecture.mvp.contract.MainActivityContract;
import cc.ylike.corelibrary.http.ThrowableTool;
import cc.ylike.corelibrary.mvp.BaseIView;
import cc.ylike.corelibrary.mvp.biz.BasePresenterImpl;
import cc.ylike.corelibrary.utils.L;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
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
        Observable<LoginBean> observable = apiService.doLogin(map);
        observable
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
                        String msg = ThrowableTool.backMsg(e);
                        L.e(msg);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }




}
