package com.ysdc.movie.ui.movielist;

import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.ui.base.MvpPresenter;

import java.util.List;

import io.reactivex.Single;

public interface MovieListMvpPresenter<V extends MovieListMvpView> extends MvpPresenter<V> {

    boolean hasMoreMovies();

    boolean isQueryInProgress();

    void initializeMovies();

    Single<List<Movie>> getMovies();

    Single<Movie> getMovie(int movieId);

    Single<List<Movie>> getMoreMovies();

    boolean isMovieListInitialized();
}
