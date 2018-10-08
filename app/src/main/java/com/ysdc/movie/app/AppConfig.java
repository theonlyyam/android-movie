package com.ysdc.movie.app;

import android.app.Application;
import android.content.pm.PackageManager;

import com.ysdc.movie.BuildConfig;

import timber.log.Timber;

import static com.ysdc.movie.utils.AppConstants.EMPTY_STRING;

/**
 * Class used to access all application specific information, such as version, MovieDB Key or debug mode
 */

public class AppConfig implements GeneralConfig {

    private final Application application;
    private String locale;

    public AppConfig(Application application) {
        this.application = application;
    }

    @Override
    public String getFormattedVersion() {
        return "v" + BuildConfig.VERSION_NAME + " #" + BuildConfig.VERSION_CODE;
    }

    @Override
    public String getVersionName() {
        try {
            return application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "getApplicationVersionName(): %s", e.getMessage());
        }
        return EMPTY_STRING;
    }

    @Override
    public long getVersionCode() {
        try {
            return application.getPackageManager().getPackageInfo(application.getPackageName(), 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "getVersionCode(): %s", e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public String getMovieDbKey() {
        return BuildConfig.API_KEY;
    }
}