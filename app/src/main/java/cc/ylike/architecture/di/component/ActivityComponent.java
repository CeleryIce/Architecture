package cc.ylike.architecture.di.component;




import cc.ylike.architecture.MainActivity;
import cc.ylike.architecture.di.module.ActivityModule;
import dagger.Component;

/**
 * Created by xsl on 2017/4/10.
 */
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);


}

