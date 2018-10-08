package com.ysdc.movie.data.network.mapper;

import com.google.gson.GsonBuilder;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.data.network.model.DiscoverMovieResponse;
import com.ysdc.movie.data.network.model.MovieResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.ysdc.movie.utils.AppConstants.MOVIE_DB_DATE_FORMAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class NetworkMovieMapperTest {
    private static final String BASE_IMG_URL = "http://foo.bar/";
    private static final String DEFAULT_IMG_SIZE = "500W";

    private NetworkMovieMapper mapper;

    @Before
    public void setUp() {
        mapper = new NetworkMovieMapper();
        mapper.setImageDefaultSize(DEFAULT_IMG_SIZE);
        mapper.setImageBaseUrl(BASE_IMG_URL);
    }

    @Test
    public void parseMovieResponseTest() throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(MOVIE_DB_DATE_FORMAT);
        Date releaseDate = dateFormat.parse("2018-05-15");

        MovieResponse response = readJsonFromFile("movie_details.json", MovieResponse.class);
        Movie movie = mapper.parseMovieResponse(response);

        assertNotNull(movie);
        assertEquals(383498, movie.getId());
        assertEquals("Deadpool 2", movie.getTitle());
        assertEquals("Prepare for the Second Coming.", movie.getTagline());
        assertEquals("Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.", movie.getOverview());
        assertEquals(7.5f, movie.getVoteAverage(), 0.0f);
        assertEquals(BASE_IMG_URL + DEFAULT_IMG_SIZE + "/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg", movie.getPoster());
        assertEquals(releaseDate, movie.getReleaseDate());
        assertEquals(3, movie.getGenres().size());
        assertEquals("Action", movie.getGenres().get(0));
    }

    @Test
    public void parseMoviesResponse() throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(MOVIE_DB_DATE_FORMAT);
        Date releaseDate = dateFormat.parse("2018-10-03");

        DiscoverMovieResponse response = readJsonFromFile("discover_movies.json", DiscoverMovieResponse.class);

        List<Movie> movies = mapper.parseMoviesResponse(response);

        assertNotNull(movies);
        assertEquals(20, movies.size());

        Movie movie = movies.get(0);
        assertEquals(335983, movie.getId());
        assertEquals("Venom", movie.getTitle());
        assertNull(movie.getTagline());
        assertEquals("When Eddie Brock acquires the powers of a symbiote, he will have to release his alter-ego “Venom” to save his life.", movie.getOverview());
        assertEquals(6.6f, movie.getVoteAverage(), 0.0f);
        assertEquals(BASE_IMG_URL + DEFAULT_IMG_SIZE + "/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg", movie.getPoster());
        assertEquals(releaseDate, movie.getReleaseDate());
        assertEquals(0, movie.getGenres().size());
    }

    /**
     * Private method used to convert a json file from our assets to an instance of an object. In this project i use it only in this class, so it is a private method,
     * but in a real project should be stored in a shared instance between the test classes.
     * @param filepath the file path
     * @param clazz the result class type
     * @param <T> the instance of the class we will create in that method
     * @return the instance of the class we will create in that method
     * @throws IOException if we are note able to read the given file path, raise an exception
     */
    private <T> T readJsonFromFile(String filepath, Type clazz) throws IOException {
        return new GsonBuilder().create().fromJson(readTextFileFromAssets(filepath), clazz);
    }

    /**
     * Read a text file from our test assets
     * @param filepath the file path
     * @return a string representing the content of the file path given in parameter
     * @throws IOException if we are note able to read the given file path, raise an exception
     */
    private String readTextFileFromAssets(String filepath) throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream(filepath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        is.close();
        return sb.toString();
    }
}
