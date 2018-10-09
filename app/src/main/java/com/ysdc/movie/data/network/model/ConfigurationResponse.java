package com.ysdc.movie.data.network.model;

import android.support.annotation.VisibleForTesting;

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

    public String getImageBaseUrl() {
        return images.imageBaseUrl;
    }

    public String getSecureImageVaseUrl() {
        return images.secureImageBaseUrl;
    }

    @VisibleForTesting
    public void setSecureImageVaseUrl(String url) {
        images.secureImageBaseUrl = url;
    }

    @VisibleForTesting
    public void setImageBaseUrl(String url) {
        images.imageBaseUrl = url;
    }

    private class Images {
        @SerializedName("base_url")
        private String imageBaseUrl;
        @SerializedName("secure_base_url")
        private String secureImageBaseUrl;
    }
}
