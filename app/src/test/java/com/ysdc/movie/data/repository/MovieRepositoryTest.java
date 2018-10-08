package com.ysdc.movie.data.repository;

import com.google.gson.Gson;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.data.network.mapper.NetworkMovieMapper;
import com.ysdc.movie.data.network.model.ConfigurationResponse;
import com.ysdc.movie.data.network.model.DiscoverMovieResponse;
import com.ysdc.movie.data.network.model.MovieDbErrorResponse;
import com.ysdc.movie.data.network.model.MovieResponse;
import com.ysdc.movie.data.network.service.MovieNetworkService;
import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.exception.MovieDbException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import test.helper.rx.RxSchedulersTestRule;

import static com.ysdc.movie.data.prefs.MyPreferences.IMAGES_BASE_URL;
import static com.ysdc.movie.data.prefs.MyPreferences.SECURE_IMAGES_BASE_URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryTest {

    @Rule
    public RxSchedulersTestRule rxSchedulersTestRule = new RxSchedulersTestRule();
    @Mock
    MovieNetworkService movieNetworkService;
    @Mock
    MyPreferences preferences;
    @Mock
    NetworkMovieMapper networkMovieMapper;
    @Mock
    Gson gson;
    @Mock
    HttpException exception;
    @Mock
    Response errorResponse;
    @Mock
    ResponseBody errorBody;

    private MovieRepository movieRepository;

    @Before
    public void setUp() {
        movieRepository = new MovieRepository(movieNetworkService, preferences, gson, networkMovieMapper);
    }

    @Test
    public void retrieveConfigurationTestSuccess() {
        String url1 = "URL_1";
        String url2 = "URL_2";
        ConfigurationResponse configurationResponse = new ConfigurationResponse();
        configurationResponse.setImageBaseUrl(url1);
        configurationResponse.setSecureImageVaseUrl(url2);

        when(movieNetworkService.getconfiguration()).thenReturn(Single.just(configurationResponse));

        TestObserver<ConfigurationResponse> testObserver = TestObserver.create();
        movieRepository.retrieveConfiguration().subscribe(testObserver);

        rxSchedulersTestRule.ioScheduler().triggerActions();
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertTerminated();

        verify(movieNetworkService, times(1)).getconfiguration();
        verify(preferences, times(1)).set(IMAGES_BASE_URL, url1);
        verify(preferences, times(1)).set(SECURE_IMAGES_BASE_URL, url2);
    }

    @Test
    public void retrieveConfigurationTestFailure() throws IOException {
        int errorCode = 401;
        String errorString = "Invalid API key: You must be granted a valid key.";
        String errorBodyString = "{\"status_code\":" + errorCode + ",\"status_message\": \"" + errorString + "\"}";
        MovieDbErrorResponse response = new MovieDbErrorResponse();
        response.setCode(errorCode);
        response.setMessage(errorString);

        when(movieNetworkService.getconfiguration()).thenReturn(Single.error(exception));
        when(exception.code()).thenReturn(errorCode);
        when(exception.response()).thenReturn(errorResponse);
        when(errorResponse.errorBody()).thenReturn(errorBody);
        when(errorBody.string()).thenReturn(errorBodyString);
        when(gson.fromJson(errorBodyString, MovieDbErrorResponse.class)).thenReturn(response);

        TestObserver<ConfigurationResponse> testObserver = TestObserver.create();
        movieRepository.retrieveConfiguration().subscribe(testObserver);

        rxSchedulersTestRule.ioScheduler().triggerActions();
        testObserver.awaitTerminalEvent();
        testObserver.assertTerminated();
        testObserver.assertError(MovieDbException.class);

        assertEquals(1, testObserver.errors().size());
        Throwable error = testObserver.errors().get(0);
        assertEquals(errorString, error.getMessage());

        verify(preferences, times(1)).remove(IMAGES_BASE_URL);
        verify(preferences, times(1)).remove(SECURE_IMAGES_BASE_URL);
    }

    @Test
    public void getMoviesTestSuccess() {
        DiscoverMovieResponse response = new DiscoverMovieResponse();
        response.setPages(19043);
        response.setTotal(380855);
        List<Movie> movies = new ArrayList<>();
        movies.add(Movie.builder().withId(1).build());
        movies.add(Movie.builder().withId(2).build());

        when(movieNetworkService.getLatestMovie(any(), any(), any(), any())).thenReturn(Single.just(response));
        when(networkMovieMapper.parseMoviesResponse(response)).thenReturn(movies);

        TestObserver<List<Movie>> testObserver = TestObserver.create();
        movieRepository.getMovies(1, new Date(), null).subscribe(testObserver);

        assertEquals(Integer.valueOf(0), movieRepository.getLastSearchTotalPages());
        assertEquals(Integer.valueOf(0), movieRepository.getLastSearchTotalResults());

        rxSchedulersTestRule.ioScheduler().triggerActions();
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertTerminated();
        testObserver.assertResult(movies);
        verify(movieNetworkService, times(1)).getLatestMovie(any(), any(), any(), any());

        rxSchedulersTestRule.mainScheduler().triggerActions();
        assertEquals(Integer.valueOf(19043), movieRepository.getLastSearchTotalPages());
        assertEquals(Integer.valueOf(380855), movieRepository.getLastSearchTotalResults());
    }

    @Test
    public void getMovieTestSuccess() {
        Integer movieId = 3;
        MovieResponse response = new MovieResponse();
        Movie movie = Movie.builder().withId(movieId).build();

        when(movieNetworkService.getMovieDetails(movieId)).thenReturn(Single.just(response));
        when(networkMovieMapper.parseMovieResponse(response)).thenReturn(movie);

        TestObserver<Movie> testObserver = TestObserver.create();
        movieRepository.getMovie(movieId).subscribe(testObserver);
        rxSchedulersTestRule.ioScheduler().triggerActions();
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertTerminated();
        testObserver.assertResult(movie);
        verify(movieNetworkService, times(1)).getMovieDetails(movieId);


    }
}
