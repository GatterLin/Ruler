package com.ruler.csw.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ruler.csw.global.RulerInfoHandler;

/**
 * Created by 丛 on 2018/6/14 0014.
 */
public class LengthTextView implements RulerInfoHandler {
    private float textX;
    private float textY; // 字体的左下角坐标值
    private String lengthText;

    public LengthTextView() {
        textX = getScreenW() / 20f * 1f; // 距离字体绘制位置
        textY = getScreenH() / 20f * 19f; // 距离字体绘制位置
        lengthText = "";
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(getScreenW() / 22f); // 设置显示距离字体大概100px(1080p) 比例

        // 绘制距离文本
        paint.setShadowLayer(getScreenW() / 960,
                getScreenW() / 1920, getScreenW() / 1920, Color.GRAY); // 比例1080p
        canvas.drawText(lengthText, textX, textY, paint);
        paint.setShadowLayer(0, 0, 0, Color.WHITE);
    }

    public void logic(String lengthString) {
        this.lengthText = lengthString;
    }

}
