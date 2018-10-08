package com.ysdc.movie.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.ysdc.movie.BuildConfig;
import com.ysdc.movie.injection.component.AppComponent;
import com.ysdc.movie.injection.component.DaggerAppComponent;
import com.ysdc.movie.injection.module.AppModule;
import com.ysdc.movie.utils.CrashlyticsUtils;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Override the default Android application to initialize some of our dependencies (logging, firebase tools, etc.)
 */

public class MyApplication extends MultiDexApplication {

    private AppComponent appComponent;

    /**
     * Called when the application is starting, before any other application objects have been
     * created. We initialize the external libraries here also, as it is always called, at the real
     * starting of the app, and only once.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();

        if (BuildConfig.DEBUG) {
            //Init Timber
            Timber.plant(new Timber.DebugTree());
            //Init Stetho debug bridge
            initStetho();
            //Init LeakCanary
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        } else {
            Fabric.with(this, new Crashlytics());
            Timber.plant(new CrashlyticsUtils.CrashlyticsTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(MyApplication.this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * Method used to set a test component
     *
     * @param appComponent the dagger test appComponent
     */
    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
