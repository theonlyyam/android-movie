package com.ysdc.movie.ui.splash;

import com.ysdc.movie.ui.base.MvpView;

/**
 * Action specific to the Splash activity.
 * Useful for its presenter, to know what are the actions available.
 */

public interface SplashMvpView extends MvpView {

    /**
     * Method called when we have finish to initialize the app (get the configuration)
     */
    void applicationInitialized();

    /**
     * Method called if an error occurred during the retrieval of the configuration
     * @param throwable the error raised during the getConfig
     */
    void onConfigurationRetrivalError(Throwable throwable);
}
