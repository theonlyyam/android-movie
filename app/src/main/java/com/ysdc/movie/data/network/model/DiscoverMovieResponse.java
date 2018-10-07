package com.ysdc.movie.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity that contains the response of the query 'discover/movie' to theMovieDB. we only keep the attributes we are interested in.
 * This class reuse the MovieResponse definition, used when we query for the details of a movie.
 */
public class DiscoverMovieResponse {
    @SerializedName("total_results")
    private Integer total;
    @SerializedName("total_pages")
    private Integer pages;
    @SerializedName("results")
    private List<MovieResponse> movies;

    public DiscoverMovieResponse() {
        movies = new ArrayList<>();
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getPages() {
        return pages;
    }

    public List<MovieResponse> getMovies() {
        return movies;
    }
}
