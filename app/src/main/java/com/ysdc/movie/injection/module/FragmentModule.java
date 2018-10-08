package com.ysdc.movie.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.injection.annotations.ActivityScope;
import com.ysdc.movie.injection.annotations.FragmentScope;
import com.ysdc.movie.ui.movielist.MovieListMvpPresenter;
import com.ysdc.movie.ui.movielist.MovieListMvpView;
import com.ysdc.movie.ui.movielist.MovieListPresenter;

import dagger.Module;
import dagger.Provides;

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
    MovieListMvpPresenter<MovieListMvpView> provideMovieListPresenter(MovieRepository movieRepository) {
        return new MovieListPresenter<>(movieRepository);
    }
}
