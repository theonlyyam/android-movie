package com.ysdc.movie.data.repository;

import com.google.gson.Gson;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.data.network.mapper.NetworkMovieMapper;
import com.ysdc.movie.data.network.model.MovieDbErrorResponse;
import com.ysdc.movie.data.network.service.MovieNetworkService;
import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.exception.MovieDbException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

import static com.ysdc.movie.data.prefs.MyPreferences.IMAGES_BASE_URL;
import static com.ysdc.movie.data.prefs.MyPreferences.SECURE_IMAGES_BASE_URL;
import static com.ysdc.movie.utils.AppConstants.MOVIE_DB_DATE_FORMAT;

/**
 * Class responsible to provide all movie data to our app. We use this class to hide the implementation of the origin of the data (network, DB, preferences, memory, etc.)
 * This class is also responsible to store the data we want to persist.
 */
public class MovieRepository {

    //We have for the moment hardcoded the sort order, but in a real application should be a parameter.
    private static final String DEFAULT_SORT_ORDER = "release_date.desc";
    private static final Integer DEFAULT_LAST_SEARCH_VALUE = 0;

    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_NOT_FOUND = 404;

    private final NetworkMovieMapper networkMovieMapper;
    private final MovieNetworkService networkService;
    private final MyPreferences preferences;
    private final Gson gson;

    private Integer lastSearchTotalResults;
    private Integer lastSearchTotalPages;

    public MovieRepository(MovieNetworkService networkService, MyPreferences preferences, Gson gson, NetworkMovieMapper networkMovieMapper) {
        this.networkService = networkService;
        this.preferences = preferences;
        this.gson = gson;
        this.networkMovieMapper = networkMovieMapper;
    }

    /**
     * @return the total number of movies found during the last search.
     */
    public Integer getLastSearchTotalResults() {
        return lastSearchTotalResults;
    }

    /**
     * @return the total number of pages existing for the last search.
     */
    public Integer getLastSearchTotalPages() {
        return lastSearchTotalPages;
    }

    /**
     * Method to retrieve configuration from the MovieDB. We only use for the moment the images URLs, but we should, if we had more time, use the images size to
     * query the best adapted images depending on the phone resolution.
     *
     * @return A completable, simply used to inform the caller that the operation completed, or an error if anything went bad.
     */
    public Completable retrieveConfiguration() {
        return networkService.getconfiguration()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(configurationResponse -> {
                    preferences.set(IMAGES_BASE_URL, configurationResponse.getImageBaseUrl());
                    preferences.set(SECURE_IMAGES_BASE_URL, configurationResponse.getSecureImageVaseUrl());
                }).onErrorResumeNext(throwable -> {
                    preferences.remove(IMAGES_BASE_URL);
                    preferences.remove(SECURE_IMAGES_BASE_URL);
                    return Single.error(analyzeError(throwable));
                })
                .ignoreElement();
    }

    /**
     * Get a list of the most movies
     *
     * @param pageNumber the page number we want to see, as the service return 20 movies at a time (sadly this offset is not configurable)
     * @param beforeDate movies release before this date.
     * @param year       optional attribute, if we want a specific year
     * @return a list of movies, encapsulated in a Single RXJava object, or an error if anything went bad.
     */
    public Single<List<Movie>> getMovies(Integer pageNumber, @NotNull Date beforeDate, @Nullable Integer year) {
        return Single.defer(() -> {
            resetLastSearchValues();
            SimpleDateFormat dateFormat = new SimpleDateFormat(MOVIE_DB_DATE_FORMAT, Locale.getDefault());
            return networkService.getLatestMovie(pageNumber, dateFormat.format(beforeDate), DEFAULT_SORT_ORDER, year)
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess(discoverMovieResponse -> {
                        lastSearchTotalPages = discoverMovieResponse.getPages();
                        lastSearchTotalResults = discoverMovieResponse.getTotal();
                    })
                    .map(networkMovieMapper::parseMoviesResponse)
                    .onErrorResumeNext(throwable -> Single.error(analyzeError(throwable)));
        });
    }

    /**
     * Get details of a movie
     *
     * @param movieId the Id of the movie we are interested in
     * @return a movie object, encapsulated in a Single RXJava object, or an error if anything went bad.
     */
    public Single<Movie> getMovie(Integer movieId) {
        return networkService.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .map(networkMovieMapper::parseMovieResponse)
                .onErrorResumeNext(throwable -> Single.error(analyzeError(throwable)));
    }

    /**
     * MovieDB can return error in a nice format, using a code and a message. In this POC I have decide to use only the message, but in a real app we should use the code
     * and in our application react deferently depending on this code (retry, go to login page, etc.)
     *
     * @param throwable the exception received from our retrofit network call
     * @return the exception we want to return (Either a specific DBMovie exception, or if the error is something else we simply push it back)
     */
    private Throwable analyzeError(Throwable throwable) {
        if (throwable instanceof HttpException &&
                (((HttpException) throwable).code() == HTTP_NOT_FOUND || ((HttpException) throwable).code() == HTTP_UNAUTHORIZED)) {
            ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
            if (responseBody != null) {
                MovieDbErrorResponse response = null;
                try {
                    response = gson.fromJson(responseBody.string(), MovieDbErrorResponse.class);
                } catch (IOException e) {
                    Timber.e(e, "Exception appeared when we tried to call string() on the response body object. Should never happen (like all exception :P)");
                }
                if (response.getCode() != null && response.getMessage() != null) {
                    Timber.e(throwable, "Error during a call to MovieDB: %d / %s", response.getCode(), response.getMessage());
                    return new MovieDbException(response.getMessage());
                }
            }
        }
        return throwable;
    }

    /**
     * Reset the last search results
     */
    private void resetLastSearchValues() {
        this.lastSearchTotalPages = DEFAULT_LAST_SEARCH_VALUE;
        this.lastSearchTotalResults = DEFAULT_LAST_SEARCH_VALUE;
    }
}