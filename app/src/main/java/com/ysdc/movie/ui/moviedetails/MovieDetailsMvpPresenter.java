package com.ysdc.movie.ui.moviedetails;

import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.ui.base.MvpPresenter;

public interface MovieDetailsMvpPresenter<V extends MovieDetailsMvpView> extends MvpPresenter<V> {

    void setMovie(Movie movie);

    String getMovieImg();

    String getOriginalTitle();

    String getReleaseDate();

    String getGenre();

    String getVote();

    String getOverview();

    String getTitle();
}
