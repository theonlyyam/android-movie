package com.ysdc.movie.utils;

/**
 * Contains the constants used in different part of our application.
 */

public class AppConstants {

    //Shared
    public static final String EMPTY_STRING = "";

    //Network constant, used in the header of all request to MovieDB
    public static final String NETWORK_KEY_API = "api_key";
    public static final String NETWORK_KEY_LANGUAGE = "language";
    public static final String NETWORK_KEY_REGION = "region";

    //Date format used by TheMovieDB endpoints requests and responses
    public static final String MOVIE_DB_DATE_FORMAT = "yyyy-MM-dd";
    //Date format used by the filter view
    public static final String FILTER_DATE_FORMAT = "MMMM yyyy";

    //Used when we want to display a list of strings, comma separated (like for genre)
    public static final String LIST_SEPARATOR = ", ";

    //Used in the filter view when no filters have been set
    public static final String EMPTY_FILTER = "-";

}
