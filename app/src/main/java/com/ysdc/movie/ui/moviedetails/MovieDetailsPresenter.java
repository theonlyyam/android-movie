package com.ysdc.movie.ui.moviedetails;


import android.text.TextUtils;

import com.ysdc.movie.R;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.ui.base.BasePresenter;
import com.ysdc.movie.ui.movielist.MovieListMvpPresenter;
import com.ysdc.movie.ui.movielist.MovieListMvpView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.ysdc.movie.utils.AppConstants.EMPTY_STRING;
import static com.ysdc.movie.utils.AppConstants.LIST_SEPARATOR;
import static com.ysdc.movie.utils.AppConstants.MOVIE_DB_DATE_FORMAT;

/**
 * Presenter of the movie list fragment. Responsable of the call for the movie list, and also to get the movie details.
 * @param <V>
 */
public class MovieDetailsPresenter<V extends MovieDetailsMvpView> extends BasePresenter<V> implements MovieDetailsMvpPresenter<V> {

    private Movie movie;

    public MovieDetailsPresenter() {
        super();
    }

    @Override
    public void setMovie(Movie movie){
        this.movie = movie;
    }

    @Override
    public String getMovieImg() {
        return movie.getPoster();
    }

    @Override
    public String getOriginalTitle() {
        return movie.getOriginalTitle();
    }

    @Override
    public String getReleaseDate() {
        if(movie.getReleaseDate() != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat(MOVIE_DB_DATE_FORMAT, Locale.getDefault());
            return dateFormat.format(movie.getReleaseDate());
        }
        return EMPTY_STRING;
    }

    @Override
    public String getGenre() {
        return TextUtils.join(LIST_SEPARATOR, movie.getGenres());
    }

    @Override
    public String getVote() {
        return getMvpView().provideResources().getQuantityString(R.plurals.vote_format, movie.getVoteCount(), movie.getVoteAverage(), movie.getVoteCount());
    }

    @Override
    public String getOverview() {
        return movie.getOverview();
    }

    @Override
    public String getTitle() {
        return movie.getTitle();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onDetach() {
        getCompositeDisposable().dispose();
        super.onDetach();
    }

}
