package com.ysdc.movie.ui.splash;

import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter linked to the splash Activity. Contains all the actions implementation of the splash activity that are not related to the UI.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    private final MovieRepository movieRepository;

    public SplashPresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void initApplication() {
        getCompositeDisposable().add(
                movieRepository.retrieveConfiguration()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> getMvpView().applicationInitialized(), throwable -> getMvpView().onConfigurationRetrivalError(throwable))
        );
    }


}
