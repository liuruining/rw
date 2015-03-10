package com.liuruining.rw;

import android.app.Application;

import com.liuruining.rw.common.BaseConfig;


/**
 * Created by nielongyu on 15/3/9.
 */
public class RwApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseConfig.init(this);
    }

}
