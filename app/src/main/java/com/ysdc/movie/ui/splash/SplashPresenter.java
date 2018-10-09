package com.ysdc.movie.ui.splash;

import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.ui.base.BasePresenter;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter linked to the splash Activity. Contains all the actions implementation of the splash activity that are not related to the UI.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    private final MovieRepository movieRepository;
    private final MyPreferences preferences;

    public SplashPresenter(MovieRepository movieRepository, MyPreferences preferences) {
        this.movieRepository = movieRepository;
        this.preferences = preferences;
    }

    @Override
    public void initApplication() {
        getCompositeDisposable().add(
                movieRepository.retrieveConfiguration()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            //I have decided that when you restart the app, i reset the filters to their default value. It is a personal choice, easy to change
                            preferences.set(MyPreferences.FILTER_FROM_DATE, 0L);
                            preferences.set(MyPreferences.FILTER_TO_DATE, new Date().getTime());
                            getMvpView().applicationInitialized();
                        }, throwable -> getMvpView().onConfigurationRetrivalError(throwable))
        );
    }


}
