package com.ysdc.movie.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Entity that contains the response of the query 'configuration' to theMovieDB. we only keep the attributes we are interested in.
 */
public class ConfigurationResponse {
    @SerializedName("images")
    private Images images;

    public ConfigurationResponse() {
        this.images = new Images();
    }

    class Images {
        @SerializedName("base_url")
        private String imageBaseUrl;
        @SerializedName("secure_base_url")
        private String secureImageBaseUrl;
    }
}
