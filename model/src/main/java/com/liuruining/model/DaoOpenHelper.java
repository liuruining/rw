package com.liuruining.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liuruining.model.dao.DaoMaster;
import com.liuruining.model.dao.WordDao;
import com.liuruining.model.dao.WordLocationDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * Created by nielongyu on 15/3/9.
 */
public class DaoOpenHelper extends DaoMaster.OpenHelper {
    private Context mContext;
    public static final String DB_NAME = "book.db";

    public DaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        mContext = context;
    }

    public static SQLiteDatabase openDataBase(Context context) {
        String DB_PATH = File.separator + "data" + File.separator + "data" + File.separator + "com.liuruining.rw" + File.separator + "databases" + File.separator;
        String DB_NAME = "dic.db";
        File dir = new File(DB_PATH);
        if (!dir.exists())
            dir.mkdir();
        if (!(new File(DB_PATH + DB_NAME)).exists()) {
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[8192];
                int count = 0;
                InputStream is = context.getResources().openRawResource(
                        R.raw.dic);
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return openDataBase(context);
        } else {
            return SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        }

    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = null;
        try {
            db = openDataBase(mContext);
            WordDao.createTable(db, true);
            WordLocationDao.createTable(db, true);
        } catch (Throwable t) {
            if (this.mContext != null) {
                super.close();
                this.mContext.deleteDatabase(DB_NAME);
                db = openDataBase(mContext);
                WordDao.createTable(db, true);
            }
        }
        return db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
