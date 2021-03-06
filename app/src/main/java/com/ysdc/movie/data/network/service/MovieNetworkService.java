package com.ysdc.movie.data.network.service;

import com.ysdc.movie.data.network.model.ConfigurationResponse;
import com.ysdc.movie.data.network.model.DiscoverMovieResponse;
import com.ysdc.movie.data.network.model.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface that contains the definition of the network call we do using retrofit.
 * The default parameters we send with all queries are defined in interceptors, attached to the OkHttpClient client.
 * @see com.ysdc.movie.data.network.NetworkServiceCreator
 */
public interface MovieNetworkService {

    String PARAMETER_ORDER = "sort_by";
    String PARAMETER_RELEASE_BEFORE = "release_date.lte";
    String PARAMETER_RELEASE_AFTER = "release_date.gte";
    String PARAMETER_PAGE = "page";
    String PARAMETER_MOVIE_ID = "movie_id";

    String URL_CONFIGURATION = "configuration";
    String URL_DISCOVER_MOVIE = "discover/movie";
    String URL_MOVIE_DETAILS = "movie/{" + PARAMETER_MOVIE_ID + "}";

    @GET(URL_CONFIGURATION)
    Single<ConfigurationResponse> getconfiguration();

    @GET(URL_DISCOVER_MOVIE)
    Single<DiscoverMovieResponse> getLatestMovie(@Query(PARAMETER_PAGE) Integer page, @Query(PARAMETER_RELEASE_BEFORE) String beforeDate,
                                                 @Query(PARAMETER_RELEASE_AFTER) String afterDate, @Query(PARAMETER_ORDER) String orderBy);

    @GET(URL_MOVIE_DETAILS)
    Single<MovieResponse> getMovieDetails(@Path(PARAMETER_MOVIE_ID) Integer movieId);

}
