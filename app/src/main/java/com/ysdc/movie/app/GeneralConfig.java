package com.ysdc.movie.app;

/**
 * interface used to access all application specific information, such as version
 */

public interface GeneralConfig {

    /**
     * @return true if the gradle build is in debug
     */
    boolean isDebug();

    /**
     * @return the API key used during the request to the backend
     */
    String getMovieDbKey();

}