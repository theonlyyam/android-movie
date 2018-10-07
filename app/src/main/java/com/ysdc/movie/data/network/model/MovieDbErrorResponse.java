package com.ysdc.movie.data.network.model;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

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
    void setMessage(String message) {
        this.message = message;
    }

    @VisibleForTesting
    void setCode(Integer code) {
        this.code = code;
    }
}
