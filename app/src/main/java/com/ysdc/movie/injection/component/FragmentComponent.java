package com.ysdc.movie.injection.component;

import com.ysdc.movie.injection.annotations.FragmentScope;
import com.ysdc.movie.injection.module.FragmentModule;
import com.ysdc.movie.ui.movielist.MovieListFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MovieListFragment movieListFragment);

}
