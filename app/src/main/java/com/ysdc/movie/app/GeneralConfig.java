package com.ysdc.movie.app;

import com.ysdc.movie.data.model.Version;

/**
 * interface used to access all application specific information, such as version
 * Created by david on 5/10/18.
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
     * @return an Version Object representing the Current version of the App
     */
    Version getCurrentAppVersion();

    /**
     *
     * @return true if the gradle build is in debug
     */
    boolean isDebug();
}