package com.ysdc.movie.exception;

import java.io.IOException;

/**
 * Exception raised when we have no connection
 */

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}
