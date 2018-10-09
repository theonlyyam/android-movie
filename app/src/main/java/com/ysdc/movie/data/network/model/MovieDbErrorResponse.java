package com.ysdc.movie.data.network.model;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

/**
 * Class that represent the content of an error returned by MovieDB. When we receive a 401 or 404 from Movie DB, we can cast the body content to this class
 * to get more details about the error
 */
public class MovieDbErrorResponse {
    @SerializedName("status_message")
    private String message;
    @SerializedName("status_code")
    private Integer code;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    @VisibleForTesting
    public void setMessage(String message) {
        this.message = message;
    }

    @VisibleForTesting
    public void setCode(Integer code) {
        this.code = code;
    }
}
