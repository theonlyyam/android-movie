package com.ysdc.movie.exception;

import java.io.IOException;

/**
 * Class used to encapsulate an error coming from a call to the MovieDB website
 */
public class MovieDbException extends IOException {

    public MovieDbException(String message) {
        super(message);
    }
}
