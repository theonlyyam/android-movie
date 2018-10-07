package com.ysdc.movie.data.network.service;

import com.ysdc.movie.data.network.model.DiscoverMovieResponse;
import com.ysdc.movie.data.network.model.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieNetworkService {

    //TODO: remove 2017-10-01

    String PARAMETER_ORDER = "sort_by";
    String PARAMETER_RELEASE_BEFORE = "primary_release_date.lte";
    String PARAMETER_PAGE = "page";
    String PARAMETER_FILTER_YEAR = "primary_release_year";
    String PARAMETER_MOVIE_ID = "movie_id";

    String URL_DISCOVER_MOVIE = "discover/movie";
    String URL_MOVIE_DETAILS = "/movie/{" + PARAMETER_MOVIE_ID + "}";

    @GET(URL_DISCOVER_MOVIE)
    Single<DiscoverMovieResponse> getLatestMovie(@Query(PARAMETER_PAGE) int page, @Query(PARAMETER_RELEASE_BEFORE) String beforeDate,
                                                 @Query(PARAMETER_ORDER) String orderBy, @Query(PARAMETER_FILTER_YEAR) Integer Year);

    @GET(URL_MOVIE_DETAILS)
    Single<MovieResponse> getMovieDetails(@Path(PARAMETER_MOVIE_ID) Integer movieId);

}
