package com.liuruining.rw.common;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by nielongyu on 15/3/8.
 */
public class BaseConfig {
    public static String channel;
    public static int width;
    public static int height;
    public static float density;
    public static int densityDpi;
    private static boolean isDisplayInit;

    public static void init(Context context) {
        initDisplay(context);
    }

    public static void initDisplay(Context context) {
        if ((!isDisplayInit) && (context.getResources() != null)) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            width = metrics.widthPixels;
            height = metrics.heightPixels;
            density = metrics.density;
            densityDpi = metrics.densityDpi;
            isDisplayInit = true;
        }
    }

    public static int dp2px(int dp) {
        return (int) (dp * density);
    }

}
