package com.ysdc.movie.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.injection.annotations.ActivityScope;
import com.ysdc.movie.injection.annotations.FragmentScope;
import com.ysdc.movie.ui.filter.FilterMvpPresenter;
import com.ysdc.movie.ui.filter.FilterMvpView;
import com.ysdc.movie.ui.filter.FilterPresenter;
import com.ysdc.movie.ui.moviedetails.MovieDetailsMvpPresenter;
import com.ysdc.movie.ui.moviedetails.MovieDetailsMvpView;
import com.ysdc.movie.ui.moviedetails.MovieDetailsPresenter;
import com.ysdc.movie.ui.movielist.MovieListMvpPresenter;
import com.ysdc.movie.ui.movielist.MovieListMvpView;
import com.ysdc.movie.ui.movielist.MovieListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * All our fragment presenters are defined here, and all possible dependencies with a fragment scope
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment providesFragment() {
        return fragment;
    }

    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    @ActivityScope
    Context providesContext() {
        return fragment.getActivity();
    }

    @Provides
    @FragmentScope
    MovieListMvpPresenter<MovieListMvpView> provideMovieListPresenter(MovieRepository movieRepository, MyPreferences preferences) {
        return new MovieListPresenter<>(movieRepository, preferences);
    }

    @Provides
    @FragmentScope
    MovieDetailsMvpPresenter<MovieDetailsMvpView> provideMovieDetailsPresenter() {
        return new MovieDetailsPresenter<>();
    }

    @Provides
    @FragmentScope
    FilterMvpPresenter<FilterMvpView> provideFilterPresenter(MyPreferences preferences) {
        return new FilterPresenter<>(preferences);
    }

}
