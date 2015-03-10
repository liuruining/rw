package com.liuruining.rw;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.liuruining.model.DaoOpenHelper;
import com.liuruining.model.dao.DaoMaster;
import com.liuruining.model.dao.DaoSession;


/**
 * Created by nielongyu on 15/3/9.
 */
public class GuiceModule extends AbstractModule {
    private final Context context;

    public GuiceModule(Context context) {
        this.context = context;
    }

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    DaoSession daoSession() {
        return new DaoMaster(new DaoOpenHelper(context, DaoOpenHelper.DB_NAME, null).getWritableDatabase()).newSession();
    }

}