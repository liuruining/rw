package com.liuruining.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by nielongyu on 15/3/10.
 */
public class GsonProvider {
    private static GsonProvider provider;
    private final Gson gson;

    private GsonProvider() {
        gson = new GsonBuilder()
                .create();

    }

    public synchronized static GsonProvider getInstance() {
        if (provider == null) {
            provider = new GsonProvider();
        }
        return provider;
    }

    public Gson get() {
        return gson;
    }
}
