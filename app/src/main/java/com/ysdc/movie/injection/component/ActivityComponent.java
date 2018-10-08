package com.ysdc.movie.injection.component;

import com.ysdc.movie.injection.annotations.ActivityScope;
import com.ysdc.movie.injection.module.ActivityModule;
import com.ysdc.movie.ui.main.MainActivity;
import com.ysdc.movie.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * Created by david on 5/10/18.
 */
@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);
}
