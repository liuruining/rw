package com.liuruining.rw;

import android.app.Application;
import android.content.Context;

import com.liuruining.rw.common.BaseConfig;


/**
 * Created by nielongyu on 15/3/9.
 */
public class RwApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        BaseConfig.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
