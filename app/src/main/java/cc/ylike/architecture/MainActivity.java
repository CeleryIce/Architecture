package cc.ylike.architecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.ylike.architecture.di.component.DaggerActivityComponent;
import cc.ylike.architecture.di.module.ActivityModule;
import cc.ylike.architecture.mvp.contract.MainActivityContract;
import cc.ylike.architecture.mvp.presenter.MainActivityPresenter;
import cc.ylike.corelibrary.CoreLibrary;
import cc.ylike.corelibrary.imageloader.ImageLoader;
import cc.ylike.corelibrary.utils.L;


public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Inject
    MainActivityPresenter activityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this,this)).build().inject(this);
    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                ImageLoader.with(this)
                        .load("https://www.baidu.com/img/baidu_jgylogo3.gif")
                        .into(imageView);
                break;
            case R.id.button2:
                activityPresenter.getData();

                break;
        }
    }
}
