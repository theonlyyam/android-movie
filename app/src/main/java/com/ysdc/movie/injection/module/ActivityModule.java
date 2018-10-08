package com.ysdc.movie.injection.module;

import android.content.Context;

import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.injection.annotations.ActivityContext;
import com.ysdc.movie.injection.annotations.ActivityScope;
import com.ysdc.movie.ui.base.BaseActivity;
import com.ysdc.movie.ui.main.MainMvpPresenter;
import com.ysdc.movie.ui.main.MainMvpView;
import com.ysdc.movie.ui.main.MainPresenter;
import com.ysdc.movie.ui.splash.SplashMvpPresenter;
import com.ysdc.movie.ui.splash.SplashMvpView;
import com.ysdc.movie.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(MovieRepository movieRepository) {
        return new SplashPresenter<>(movieRepository);
    }

    @Provides
    @ActivityScope
    MainMvpPresenter<MainMvpView> provideMainPresenter(MovieRepository movieRepository) {
        return new MainPresenter<>(movieRepository);
    }
}