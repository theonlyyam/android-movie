package com.ysdc.movie.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.ysdc.movie.utils.AppConstants.EMPTY_STRING;

/**
 * Created by david on 5/10/18.
 */

public class MyPreferences {

    private static final String PREFS_FILENAME = "app_prefs_file";

    //Application parameters
    public static final String BASE_URL = "BASE_URL";


    private final SharedPreferences sharedPrefs;
    private final Gson gson;

    public MyPreferences(Context context, Gson gson) {
        this.gson = gson;
        this.sharedPrefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
    }

    public boolean contains(String key) {
        return sharedPrefs.contains(key);
    }

    public String getAsString(String key, String defaultValue) {
        return sharedPrefs.getString(key, defaultValue);
    }

    public String getAsString(String key) {
        return getAsString(key, EMPTY_STRING);
    }

    public int getAsInt(String key, int defaultValue) {
        return sharedPrefs.getInt(key, defaultValue);
    }

    public int getAsInt(String key) {
        return getAsInt(key, Integer.MIN_VALUE);
    }

    public long getAsLong(String key, long defaultValue) {
        return sharedPrefs.getLong(key, defaultValue);
    }

    public long getAsLong(String key) {
        return getAsLong(key, 0);
    }

    public boolean getAsBoolean(String key, boolean defaultValue) {
        return sharedPrefs.getBoolean(key, defaultValue);
    }

    public double getAsDouble(String key) {
        return getAsDouble(key, 0);
    }

    public Set<String> getAsSet(String key) {
        return sharedPrefs.getStringSet(key, new TreeSet<>());

    }

    public double getAsDouble(String key, double defaultValue) {
        if (!sharedPrefs.contains(key)) {
            return defaultValue;
        }
        return Double.longBitsToDouble(sharedPrefs.getLong(key, 0));
    }

    public <T> T getAsObject(String key, Class<T> myClass) {
        if (!sharedPrefs.contains(key)) {
            return null;
        }
        String objectString = sharedPrefs.getString(key, EMPTY_STRING);
        return gson.fromJson(objectString, myClass);
    }

    public void resetPreferences() {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();
    }

    public void set(String key, Map value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, gson.toJson(value));
        editor.apply();
    }

    public void set(String key, String value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void set(String key, int value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void set(String key, boolean value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void set(String key, double value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.apply();
    }

    public void set(String key, long value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void set(String key, Object object) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        String json = gson.toJson(object);
        editor.putString(key, json);
        editor.apply();
    }

    public void set(String key, Set<String> value) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public void remove(String key) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clearAsString(String... keys) {
        if (keys != null) {
            for (String key : keys) {
                set(key, EMPTY_STRING);
            }
        }
    }

    public void removeKey(String key) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.apply();
    }
}
