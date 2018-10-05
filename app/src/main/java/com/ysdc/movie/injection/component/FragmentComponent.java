package com.ysdc.movie.injection.component;

import com.ysdc.movie.injection.annotations.FragmentScope;
import com.ysdc.movie.injection.module.FragmentModule;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}
