package com.ysdc.movie.data.network.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by david on 5/10/18.
 */


public final class GsonCustom {
    private static final GsonBuilder BUILDER;

    static {
        BUILDER = new GsonBuilder();
    }

    private GsonCustom() {
        //Nothing to do
    }


    public static Gson createGson() {
        return BUILDER.create();
    }
}

