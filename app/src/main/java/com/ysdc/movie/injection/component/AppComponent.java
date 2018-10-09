package com.ysdc.movie.injection.component;

import com.ysdc.movie.app.MyApplication;
import com.ysdc.movie.injection.module.ActivityModule;
import com.ysdc.movie.injection.module.AppModule;
import com.ysdc.movie.injection.module.FragmentModule;
import com.ysdc.movie.injection.module.NetworkModule;
import com.ysdc.movie.injection.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 * Interface that contains all the module definition
 */
@Component(modules = {AppModule.class, NetworkModule.class, RepositoryModule.class})
@Singleton
public interface AppComponent {
    ActivityComponent childActivityComponent(ActivityModule activityModule);

    FragmentComponent childFragmentComponent(FragmentModule fragmentModule);

    void inject(MyApplication application);

}
