package com.ysdc.movie.injection.module;

import android.app.Application;

import com.google.gson.Gson;
import com.ysdc.movie.app.GeneralConfig;
import com.ysdc.movie.data.network.NetworkServiceCreator;
import com.ysdc.movie.data.network.service.MovieNetworkService;
import com.ysdc.movie.utils.NetworkUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by david on 5/10/18.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    NetworkServiceCreator provideNetworkServiceCreator(Gson gson, GeneralConfig generalConfig, Application application, NetworkUtils networkUtils) {
        return new NetworkServiceCreator(gson, generalConfig, application, networkUtils);
    }

    @Provides
    @Singleton
    MovieNetworkService provideMovieNetworkService(NetworkServiceCreator networkServiceCreator) {
        return networkServiceCreator.getRetrofit().create(MovieNetworkService.class);
    }
}
