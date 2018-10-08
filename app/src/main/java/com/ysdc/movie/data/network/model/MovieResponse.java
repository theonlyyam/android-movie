package com.ysdc.movie.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity that contains the response of the query '/movie/{MOVIE_ID}' to theMovieDB. we only keep the attributes we are interested in.
 * This class is also reused by another response parse, to avoid code duplicates
 */
public class MovieResponse {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private Float voteAverage;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genres")
    private List<Genre> genres;

    public MovieResponse() {
        genres = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTagline() {
        return tagline;
    }

    public String getOverview() {
        return overview;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public String getPoster() {
        return poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public class Genre {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}
