package cc.ylike.architecture.mvp.contract;

import cc.ylike.corelibrary.mvp.BaseIView;

/**
 * Created by xsl on 2018/5/17.
 */

public interface MainActivityContract {
    interface Model {
    }

    interface View extends BaseIView{

    }

    interface Presenter {

        void getData();
    }
}
