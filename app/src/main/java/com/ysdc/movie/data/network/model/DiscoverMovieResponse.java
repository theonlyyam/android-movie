package com.ysdc.movie.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DiscoverMovieResponse {
    @SerializedName("total_results")
    private Integer total;
    @SerializedName("total_pages")
    private  Integer pages;
    @SerializedName("rsults")
    private List<MovieResponse> movies;

    public DiscoverMovieResponse(){
        movies = new ArrayList<>();
    }
}
