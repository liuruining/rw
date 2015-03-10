package com.liuruining.rw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by nielongyu on 15/3/9.
 */
public class SharedPreferencesUtils {
    public static final String DATA_SET_PREFERENCE = "data_set";
    public static final String LOGIN_STORE_PREFERENCE = "loginStore";

    public static SharedPreferences getDataSharedPreferences(Context context) {
        return context.getSharedPreferences(DATA_SET_PREFERENCE,
                Context.MODE_PRIVATE);
    }

    public static SharedPreferences getUserSharedPreferences(Context context) {
        return context.getSharedPreferences(LOGIN_STORE_PREFERENCE,
                Context.MODE_PRIVATE);
    }

    public static void apply(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
