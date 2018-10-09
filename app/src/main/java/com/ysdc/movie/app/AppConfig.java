package com.ysdc.movie.app;

import com.ysdc.movie.BuildConfig;

/**
 * Class used to access all application specific information, such as MovieDB Key or debug mode
 */

public class AppConfig implements GeneralConfig {

    public AppConfig() {
        //Nothing to do
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