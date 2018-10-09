package com.ysdc.movie.injection.component;

import com.ysdc.movie.injection.annotations.FragmentScope;
import com.ysdc.movie.injection.module.FragmentModule;
import com.ysdc.movie.ui.filter.FilterFragment;
import com.ysdc.movie.ui.moviedetails.MovieDetailsFragment;
import com.ysdc.movie.ui.movielist.MovieListFragment;

import dagger.Subcomponent;

/**
 * Interface that contains our fragment in which we have to inject dependencies
 */
@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MovieListFragment movieListFragment);

    void inject(MovieDetailsFragment movieDetailsFragment);

    void inject(FilterFragment filterFragment);
}
