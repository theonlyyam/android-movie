package com.ysdc.movie.app;

/**
 * interface used to access all application specific information, such as version
 */

public interface GeneralConfig {

    /**
     * Get a formatted version of the application, used by the tracking
     *
     * @return
     */
    String getFormattedVersion();

    /**
     * @return the version name of the application
     */
    String getVersionName();

    /**
     * @return the version code of the application
     */
    long getVersionCode();

    /**
     * @return true if the gradle build is in debug
     */
    boolean isDebug();

    /**
     * Return the API key used during the request to the backend
     *
     * @return
     */
    String getMovieDbKey();

}