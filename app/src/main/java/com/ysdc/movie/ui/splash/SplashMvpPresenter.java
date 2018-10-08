package com.ysdc.movie.ui.splash;

import com.ysdc.movie.ui.base.MvpPresenter;

import io.reactivex.Completable;

/**
 * Action specific to the Splash presenter.
 * Useful for its activity, to know what are the actions available.
 */

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    /**
     * Method to init the app manually. Only available if we had an error and the user has to
     * request the app init with the retry button
     */
    void initApplication();
}