package cc.ylike.architecture;


import android.os.Bundle;



public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void doNotToolBar(boolean bool) {
        super.doNotToolBar(false);
    }
}
