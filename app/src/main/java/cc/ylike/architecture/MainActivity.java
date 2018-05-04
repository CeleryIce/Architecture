package cc.ylike.architecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.ylike.corelibrary.CoreLibrary;
import cc.ylike.corelibrary.imageloader.ImageLoader;
import cc.ylike.corelibrary.utils.L;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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


                break;
        }
    }
}
