package com.ysdc.movie.exception;

import java.io.IOException;

/**
 * Created by david on 5/10/18.
 */

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}
