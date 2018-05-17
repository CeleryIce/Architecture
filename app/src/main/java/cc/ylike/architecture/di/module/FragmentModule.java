package cc.ylike.architecture.di.module;





import android.support.v4.app.Fragment;
import cc.ylike.corelibrary.mvp.BaseIView;
import dagger.Module;
import dagger.Provides;

/**
 * @author xsl
 * @version 1.0
 * @date 2017/4/24
 * @description
 */
@Module
public class FragmentModule {

    private Fragment fragment;
    private BaseIView baseIView;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    public FragmentModule(Fragment fragment,BaseIView baseIView) {
        this.fragment = fragment;
        this.baseIView = baseIView;
    }

    @Provides
    Fragment provideFragment(){
        return fragment;
    }

    @Provides
    BaseIView provideView(){
        return baseIView;
    }

}