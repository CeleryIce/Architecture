package cc.ylike.corelibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.ylike.corelibrary.widgets.SwipeBackLibrary.app.SwipeBackActivity;


public class CeleryBaseActivity extends SwipeBackActivity {


    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected TextView navTitleText,tvAction;
    protected ImageView imageAction;
    protected Activity mContext;
    private RelativeLayout toolbar_rl_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.celery_toolbar_layout);
        mContext =this;
        findView();
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID,null);
        RelativeLayout.LayoutParams layoutParams
                = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                ,RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW,R.id.toolbar_rl_layout);
        if (null != toolbar_rl_layout){
            toolbar_rl_layout.addView(view,layoutParams);
        }
    }



    /**
     * 控制是否真的不需要toolbar
     * 默认为 false状态（即有toolbar）
     * @param bool
     */
    protected void doNotToolBar(boolean bool){
        if (bool){
            appBarLayout.setVisibility(View.GONE);
        }else {
            appBarLayout.setVisibility(View.VISIBLE);
        }
    }

    private void findView(){
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        toolbar_rl_layout = (RelativeLayout) findViewById(R.id.toolbar_rl_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navTitleText = toolbar.findViewById(R.id.mTitle);
        tvAction = toolbar.findViewById(R.id.tvAction);
        imageAction = toolbar.findViewById(R.id.imageAction);
        toolbar.setNavigationIcon(R.drawable.nav_icon);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        navTitleText.setTextColor(getResources().getColor(R.color.white));
        tvAction.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
