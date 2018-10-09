package com.ysdc.movie.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Class in charge of storing small information in the SharedPreferences.
 */

public class MyPreferences {

    //Application parameters
    private static final String PREFS_FILENAME = "app_prefs_file";

    //Base URL where we get the images from
    public static final String IMAGES_BASE_URL = "IMAGES_BASE_URL";
    public static final String SECURE_IMAGES_BASE_URL = "SECURE_IMAGES_BASE_URL";

    //Store the image size we have to query for
    public static final String DEFAULT_IMAGE_SIZE = "DEFAULT_IMAGE_SIZE";

    //Filter values
    public static final String FILTER_FROM_DATE = "FILTER_FROM_DATE";
    public static final String FILTER_TO_DATE = "FILTER_TO_DATE";


    private final SharedPreferences sharedPrefs;

    public MyPreferences(Context context) {
        this.sharedPrefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
    }

    public boolean contains(String key) {
        return sharedPrefs.contains(key);
    }

    public long getAsLong(String key) {
        return sharedPrefs.getLong(key, 0);
    }

    public void set(String key, String value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void set(String key, long value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void remove(String key) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.apply();
    }
}
