package com.ysdc.movie.injection.module;

import com.ysdc.movie.data.network.service.MovieNetworkService;
import com.ysdc.movie.data.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by david on 5/10/18.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public MovieRepository provideMovieRepository(MovieNetworkService networkService){
        return new MovieRepository(networkService);
    }
}
