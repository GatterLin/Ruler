package com.ruler.csw.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ruler.csw.global.SizeInfoHandler;
import com.ruler.csw.myinterface.DrawViewInterface;

/**
 * Created by 丛 on 2018/6/13 0013.
 */
public class ScaleView implements DrawViewInterface, SizeInfoHandler {
    // 刻度线坐标,需要时刻改变的是两个x值,而且他们的值相同
    // endY的在整10和整5处会变长
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private int scaleIndex; // 刻度值索引,从0开始
    private int color;

    public ScaleView() {
        startX = 0;
        startY = 0;
        endX = startX;
        endY = getScreenW() / 12f; // 比例 1080p下160px
        color = Color.BLUE;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // 设置刻度线颜色
        paint.setColor(Color.BLACK);
        // 设置刻度值字体大小
        paint.setTextSize(getScreenW() / 38f); // 50px(1080p) 比例
        // 设置画笔粗细
        paint.setStrokeWidth(getScreenW() / 1920f);  // 1px(1080p) 比例
        // 设置阴影效果
        paint.setShadowLayer(getScreenW() / 960, getScreenW() / 1920,
                getScreenW() / 1920, Color.GRAY);
        if ("cm".equals(getCurUnit()))
            drawCM(canvas, paint);
        else if ("inch".equals(getCurUnit())) {
            drawINCH(canvas, paint);
        }
        // 取消阴影
        paint.setShadowLayer(0, 0, 0, Color.WHITE);
    }

    @Override
    public void logic() {

    }

    private void drawCM(Canvas canvas, Paint paint) {
        // 绘制刻度线和刻度值
        for (startX = 0, startY = 0, endX = startX, endY = getScreenW() / 12f, scaleIndex = 0;
             startX < getScreenW(); scaleIndex++) {
            if (scaleIndex %10 == 0) { // 整10刻度值
                endY = getScreenW() / 12f; // 比例1080p下160px
                // 设置刻度值颜色
                paint.setColor(color);
                // 绘制刻度值
                if (scaleIndex == 10) { // 1cm处
                    canvas.drawText("1cm",
                            startX + getSize1mm(), endY, paint);
                } else {
                    canvas.drawText(String.valueOf(scaleIndex / 10),
                            startX + getSize1mm(), endY, paint);
                }
                paint.setColor(Color.BLACK);
            } else if (scaleIndex %5 == 0) { // 整5刻度值
                endY = getScreenW() / 18; // 比例1080p下106px
            } else { // 非整10整5刻度值
                endY = getScreenW() / 24; // 比例1080p下80px
            }
            // 绘制刻度线
            canvas.drawLine(startX, startY, endX, endY, paint);
            // 刻度线X坐标偏移
            startX += getSize1mm();
            endX = startX;
        }
    }

    private void drawINCH(Canvas canvas, Paint paint) {
        // 绘制刻度线和刻度值
        for (startX = 0, startY = 0, endX = startX, endY = getScreenW() / 12f, scaleIndex = 0;
             startX < getScreenW(); scaleIndex++) {
            endY = getScreenW() / 36;
            if (scaleIndex %2 == 0) {
                endY = getScreenW() / 30;
            }
            if (scaleIndex %4 == 0) {
                endY = getScreenW() / 24; // 比例1080p下80px
            }
            if (scaleIndex %8 == 0) {
                endY = getScreenW() / 18; // 比例1080p下106px
            }
            if (scaleIndex %16 == 0) {
                endY = getScreenW() / 12f; // 比例1080p下160px
                // 设置刻度值颜色
                paint.setColor(color);
                // 绘制刻度值
                if (scaleIndex == 16) { // 1cm处
                    canvas.drawText("1inch",
                            startX + getSize1mm(), endY, paint);
                } else {
                    canvas.drawText(String.valueOf(scaleIndex / 16),
                            startX + getSize1mm(), endY, paint);
                }
                paint.setColor(Color.BLACK);
            }
            // 绘制刻度线
            canvas.drawLine(startX, startY, endX, endY, paint);
            // 刻度线X坐标偏移
            startX += getSize1_32inch() * 2; // 以1/16英寸为一个刻度线
            endX = startX;
        }
    }

}
