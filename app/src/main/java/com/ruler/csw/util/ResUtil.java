package com.ruler.csw.util;

import android.content.Context;

/**
 * Created by 丛 on 2020/1/1 0001.
 */
public class ResUtil {

    public static String getString(Context context, int resId) {
        return context.getApplicationContext().getResources().getString(resId);
    }

}
