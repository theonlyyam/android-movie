package com.ysdc.movie.injection.component;

import com.ysdc.movie.injection.annotations.ActivityScope;
import com.ysdc.movie.injection.module.ActivityModule;
import com.ysdc.movie.ui.main.MainActivity;
import com.ysdc.movie.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * Interface that contains our activity in which we have to inject dependencies
 */
@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);
}
