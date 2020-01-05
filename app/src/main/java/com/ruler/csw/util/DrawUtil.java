package com.ruler.csw.util;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by 丛 on 2020/1/2 0002.
 */
public class DrawUtil {

    public static void measureText(String text, Paint paint, Rect refResult) {
        paint.getTextBounds(text, 0, text.length(), refResult);
    }

}
