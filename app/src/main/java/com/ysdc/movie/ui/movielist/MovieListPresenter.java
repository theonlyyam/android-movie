package com.ysdc.movie.ui.movielist;


import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter of the movie list fragment. Responsable of the call for the movie list, and also to get the movie details.
 * @param <V>
 */
public class MovieListPresenter<V extends MovieListMvpView> extends BasePresenter<V> implements MovieListMvpPresenter<V> {

    private static final int INITIAL_PAGE = 0;
    private final MovieRepository movieRepository;
    private final CompositeDisposable compositeDisposable;
    private final AtomicBoolean queryInProgress = new AtomicBoolean(false);
    private List<Movie> movies;
    private int currentPage;

    private Integer yearSelected;

    public MovieListPresenter(MovieRepository movieRepository) {
        super();
        this.movieRepository = movieRepository;
        this.compositeDisposable = new CompositeDisposable();
        initializeMovies();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        super.onDetach();
    }

    @Override
    public boolean hasMoreMovies() {
        return currentPage < movieRepository.getLastSearchTotalPages();
    }

    @Override
    public boolean isQueryInProgress() {
        return queryInProgress.get();
    }

    @Override
    public void initializeMovies() {
        movies = new ArrayList<>();
        currentPage = INITIAL_PAGE;
    }

    @Override
    public Single<Movie> getMovie(int movieId) {
        return null;
    }

    @Override
    public Single<List<Movie>> getMovies() {
        return Single.defer(() -> {
            queryInProgress.set(true);
            return movieRepository.getMovies(currentPage, new Date(), yearSelected)
                    .subscribeOn(Schedulers.io())
                    .onErrorResumeNext(throwable -> Single.just(new ArrayList<>()))
                    .doOnSuccess(resultList -> movies.addAll(resultList))
                    .flatMap(resultList -> Single.just(movies))
                    .doFinally(() -> queryInProgress.set(false));
        });
    }

    @Override
    public Single<List<Movie>> getMoreMovies() {
        return Single.defer(() -> {
            currentPage++;
            return getMovies();
        });
    }
}
