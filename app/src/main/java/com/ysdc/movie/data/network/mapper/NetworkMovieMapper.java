package com.ysdc.movie.data.network.mapper;

import com.crashlytics.android.Crashlytics;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.data.network.model.DiscoverMovieResponse;
import com.ysdc.movie.data.network.model.MovieResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

import static com.ysdc.movie.utils.AppConstants.MOVIE_DB_DATE_FORMAT;

/**
 * Class that transform the network response content in final Business Object, used by our application.
 */
public class NetworkMovieMapper {

    private final SimpleDateFormat dateFormat;
    private String imageBaseUrl;
    private String imageDefaultSize;

    public NetworkMovieMapper() {
        this.dateFormat = new SimpleDateFormat(MOVIE_DB_DATE_FORMAT, Locale.getDefault());
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }

    public void setImageDefaultSize(String imageDefaultSize) {
        this.imageDefaultSize = imageDefaultSize;
    }

    /**
     * Parse and convert the endpoint response to a final object that match our need, and the expected
     * format.
     *
     * @param response the network endpoint response to our query
     * @return the movie contained in the MovieResponse object returned by the endpoint
     */
    public Movie parseMovieResponse(MovieResponse response) {
        Movie.Builder builder = Movie.builder()
                .withId(response.getId())
                .withTitle(response.getTitle())
                .withOriginalTitle(response.getOriginalTitle())
                .withTagline(response.getTagline())
                .withOverview(response.getOverview())
                .withVoteAverage(response.getVoteAverage())
                .withVoteCount(response.getVoteCount())
                .withPoster(buildImageUrl(response.getPoster()));

        if (response.getReleaseDate() != null) {
            try {
                builder.withReleaseDate(dateFormat.parse(response.getReleaseDate()));
            } catch (ParseException e) {
                Timber.e("Error during date parsing for a movie: %s", response.getReleaseDate());
                Crashlytics.log("Error during date parsing for a movie: " + response.getReleaseDate());
            }
        }

        if (response.getGenres() != null) {
            List<String> genres = new ArrayList<>();
            for (MovieResponse.Genre genre : response.getGenres()) {
                genres.add(genre.getName());
            }
            builder.withGenres(genres);
        }
        return builder.build();
    }


    /**
     * Parse the response of a network call (discovery movies in this case) and convert the result
     * in a list of movie entities.
     * format.
     *
     * @param response the network endpoint response to our query
     * @return a list of movies, contained in the DiscoverMovieResponse object returned by the endpoint
     */
    public List<Movie> parseMoviesResponse(DiscoverMovieResponse response) {
        List<Movie> movies = new ArrayList<>();

        for (MovieResponse movieResponse : response.getMovies()) {
            movies.add(parseMovieResponse(movieResponse));
        }
        return movies;
    }

    /**
     * Build the movie image URL based on the base url and the image size that where set in our mapper, and the image name define in the movie object
     *
     * @param imageUrl the image id of the movie (with its extension)
     * @return the full URL path
     */
    private String buildImageUrl(String imageUrl) {
        return imageBaseUrl + imageDefaultSize + imageUrl;
    }
}
