package com.ysdc.movie.injection.module;

import com.google.gson.Gson;
import com.ysdc.movie.data.network.mapper.NetworkMovieMapper;
import com.ysdc.movie.data.network.service.MovieNetworkService;
import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.data.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Contains our repositories to inject
 */

@Module
public class RepositoryModule {

    @Provides
    public NetworkMovieMapper provideNetworkMovieMapper() {
        return new NetworkMovieMapper();
    }

    @Provides
    @Singleton
    public MovieRepository provideMovieRepository(MovieNetworkService networkService, MyPreferences preferences, Gson gson, NetworkMovieMapper networkMovieMapper) {
        return new MovieRepository(networkService, preferences, gson, networkMovieMapper);
    }
}
