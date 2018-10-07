
package com.ysdc.movie.app;

import android.app.Application;
import android.content.pm.PackageManager;

import com.ysdc.movie.BuildConfig;
import com.ysdc.movie.data.model.Version;

import timber.log.Timber;

import static com.ysdc.movie.utils.AppConstants.EMPTY_STRING;

/**
 * Class used to access all application specific information, such as version
 * Created by david on 5/10/18.
 */

public class AppConfig implements GeneralConfig {

    private String locale;
    private final Application application;

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
    public Version getCurrentAppVersion() {
        return new Version(BuildConfig.VERSION_NAME);
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