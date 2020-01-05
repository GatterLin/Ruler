package com.ruler.csw.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by 丛 on 2019/12/26 0026.
 */
public class DimensionUtil {

    /**
     * @param context
     * @param typedValue: eg, {@link TypedValue#COMPLEX_UNIT_MM}
     * @param value
     * @return
     */
    public static float convertToPixel(Context context, int typedValue, float value) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(typedValue, value, dm);
    }

}
