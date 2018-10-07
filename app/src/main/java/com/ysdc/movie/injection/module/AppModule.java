package com.ysdc.movie.injection.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ysdc.movie.app.AppConfig;
import com.ysdc.movie.app.GeneralConfig;
import com.ysdc.movie.app.MyApplication;
import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.injection.annotations.ApplicationContext;
import com.ysdc.movie.utils.NetworkUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MyApplication application;

    public AppModule(MyApplication app) {
        application = app;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public MyPreferences provideAppPreferences(@ApplicationContext Context context, Gson gson) {
        return new MyPreferences(context, gson);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public GeneralConfig provideGeneralConfig() {
        return new AppConfig(application);
    }

    @Provides
    @Singleton
    public NetworkUtils provideNetworkUtils() {
        return new NetworkUtils();
    }
}
