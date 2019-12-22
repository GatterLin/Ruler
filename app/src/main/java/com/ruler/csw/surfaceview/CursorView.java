package com.ruler.csw.surfaceview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.ruler.csw.application.App;
import com.ruler.csw.myinterface.DrawViewInterface;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by 丛 on 2018/6/13 0013.
 */
public class CursorView implements DrawViewInterface {
    // 游标线坐标
    private float lineStartX; // 改值即为测量距离
    private float lineStartY;
    private float lineEndX;
    private float lineEndY;
    // 游标饼坐标
    private float centerX;
    private float centerY;
    private float centerXInside;
    private float centerYInside;
    private float radiusOutside;
    private float radiusInside;
    private final float radiusInsideTemp;

    // 游标状态变量
    private int color;
    private float lineWidth;

    private Timer timer; // 控制游标饼动画
    private boolean isTouchCursorMove;
    private float lastMoveX;

    private boolean isCalibrationMode;

    public static boolean cursorLock; // 用于锁定游标饼位置,防止点击其他控件时,游标饼会移动

    private Activity activity;

    public CursorView(Activity activity, float startX , float endY) {
        this.activity = activity;
        // 初始化游标线坐标
        lineStartX = startX; // 在60mm处
        lineStartY = 0; // 始终为0
        lineEndX = lineStartX;
        lineEndY = endY; //比例 屏幕的1/2处,注意是横屏,所以用H
        // 初始化游标饼坐标
        centerX = lineStartX;
        centerXInside = centerX;
        centerY = lineEndY;
        centerYInside = centerY;
        radiusOutside = App.screenW / 24; // 比例 游标圆饼半径为1080p下 80px
        radiusInside = radiusOutside - (App.screenW / 120); //比例 1080p下16px
        radiusInsideTemp = radiusInside;

        color = Color.BLUE;
        lineWidth = App.screenW / 384f; // 游标线宽度,1080p下5px
        timer = new Timer();
        isTouchCursorMove = false;

        isCalibrationMode = false;

        cursorLock = false;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // 设置游标线颜色
        paint.setColor(color);
        // 设置游标线宽度
        paint.setStrokeWidth(lineWidth);
        // 画游标线
        canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, paint);
        // 设置游标饼阴影
        paint.setShadowLayer(App.screenW / 192,
                App.screenW / 384, App.screenW / 384, Color.GRAY);
        // 画外游标饼
        canvas.drawCircle(centerX, centerY, radiusOutside, paint);
        // 取消阴影
        paint.setShadowLayer(0, 0, 0, Color.WHITE);
        // 画内游标饼
        paint.setColor(Color.WHITE);
        canvas.drawCircle(centerXInside, centerYInside, radiusInside, paint);
    }

    @Override
    public void logic() {

    }

    public boolean touch(MotionEvent event) {
        float moveX = event.getX();
        float moveY = event.getY();
        // 检测是否点击到游标饼
        if(Math.abs(centerX - event.getX()) <= radiusOutside &&
                Math.abs(centerY - event.getY()) <= radiusInside && !isTouchCursorMove) { // 最后这项是因为移动时不检测
            isTouchCursorMove = true;
        }

        // 点击到游标饼
        if(isTouchCursorMove && event.getAction() == MotionEvent.ACTION_DOWN) { //最后这项是因为移动时不检测
            // 播放游标饼颜色填充动画
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    radiusInside -= 1;
                    if (radiusInside <= 0)
                        timer.cancel();
                }
            }, 0, 1);
            return true;
        } else if (isTouchCursorMove && event.getAction() == MotionEvent.ACTION_MOVE) {
            if (moveX > App.screenW) { // 游标移动不能超出横向屏幕范围
                return true;
            }
            // 游标线坐标
            lineStartX = moveX;
            lineStartY = 0;
            lineEndX = lineStartX;
            // 控制游标纵向屏幕范围
            if (!isCalibrationMode) {
                if (moveY < App.screenH / 5f * 4f) {
                    lineEndY = moveY; // 游标线长度2cm
                }
            } else {
                if (moveY > (int)(App.screenH / 8f) + (int)(App.screenH / 2.7f) &&
                        moveY < App.screenH / 5f * 4f) {
                    lineEndY = moveY;
                }
            }
            // 游标饼坐标
            centerX = lineStartX;
            centerXInside = centerX;
            centerY = lineEndY;
            centerYInside = centerY;

            return true;
        } else if (isTouchCursorMove && event.getAction() == MotionEvent.ACTION_UP) {
            isTouchCursorMove = false;
            // 确定测量距离后,格式化测量距离
            if (!isCalibrationMode) {
                lineStartX = getFormatScale(lineStartX);
                lineEndX = lineStartX;
                centerX = lineStartX;
                centerXInside = centerX;
            }

            timer.cancel();
            Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    radiusInside += 1;
                    if (radiusInside >= radiusInsideTemp) {
                        timer2.cancel();
                    }
                }
            }, 0, 1);
            return true;
        }
        if (!isTouchCursorMove && event.getAction() == MotionEvent.ACTION_DOWN) {
            lastMoveX = moveX;
            return true;
        } else if (!isTouchCursorMove && event.getAction() == MotionEvent.ACTION_MOVE) {
            if (cursorLock)
                return true;
            float moveDelta = moveX - lastMoveX;
            if (lineStartX + moveDelta < App.screenW && lineStartX + moveDelta > 0) {
                lineStartX += moveDelta;
            }
            lineEndX = lineStartX;
            centerX = lineStartX;//游标圆饼中心和游标线在一条线上
//            centerXInside = centerX;

            if (moveX - lastMoveX < 0) {
                if (centerXInside - (centerX - radiusOutside) > 5) {
                    centerXInside = centerX - radiusOutside / 10f;
                }
            } else if (moveX - lastMoveX > 0) {
                if ((centerX + radiusOutside) - centerXInside > 5) {
                    centerXInside = centerX + radiusOutside / 10f;
                }
            }
            lastMoveX = moveX;
            return true;
        } else if (!isTouchCursorMove && event.getAction() == MotionEvent.ACTION_UP) {
            // 确定测量距离后,格式化测量距离
            if (!isCalibrationMode) {
                lineStartX = getFormatScale(lineStartX);
                lineEndX = lineStartX;
                centerX = lineStartX;
                centerXInside = centerX;
            }
            centerXInside = centerX;
            return true;
        }
        return false;
    }

    private float getFormatScale(float measureLength) {
        float ration = App.sizeRationMM;
        if (App.unit.equals("cm")) {
            ration = App.sizeRationMM;
        } else if (App.unit.equals("inch")) {
            ration = App.sizeRationINCH;
        }
        float length = measureLength / ration; // 单位数字变为毫米
        length = Math.round(length); // 毫米四舍五入
        length *= ration; // 单位变为像素
        return length;
    }

    public String getLengthString() {
        float length = getFormatScale(lineStartX);
        if (App.unit.equals("cm")) {
            length /= App.sizeRationMM; // 变为毫米
            length = Math.round(length); // 四舍五入
            return (length / 10f) + "cm"; // 变为厘米
        } else if (App.unit.equals("inch")) {
            int inch1_32Num = Math.round(length / App.size1_32inch);
            int leftNum = inch1_32Num / 32;
            int rightNum = inch1_32Num % 32;
            return (leftNum == 0 ? (rightNum == 0 ? "0" : "") : leftNum) + " " +
                    (rightNum == 0 ? "" : simplyFraction(rightNum)) + "\"";
        }
        return "";
    }

    private String simplyFraction(int above) {
        int d = maxCommonDivisor(above, 32);
        above /= d;
        int below = (int) (32f / d);
        return above + "/" + below;
    }

    // 循环法求最大公约数
    private int maxCommonDivisor(int m, int n) {
        if (m < n) {// 保证m>n,若m<n,则进行数据交换
            int temp = m;
            m = n;
            n = temp;
        }
        while (m % n != 0) {// 在余数不能为0时,进行循环
            int temp = m % n;
            m = n;
            n = temp;
        }
        return n;// 返回最大公约数
    }

    public float getLineStartX() {
        return lineStartX;
    }

    public void setLineStartX(float lineStartX) {
        this.lineStartX = lineStartX;
    }

    public float getLineStartY() {
        return lineStartY;
    }

    public void setLineStartY(float lineStartY) {
        this.lineStartY = lineStartY;
    }

    public float getLineEndX() {
        return lineEndX;
    }

    public void setLineEndX(float lineEndX) {
        this.lineEndX = lineEndX;
    }

    public float getLineEndY() {
        return lineEndY;
    }

    public void setLineEndY(float lineEndY) {
        this.lineEndY = lineEndY;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setCursorPosition(float startX, float endY) {
        this.lineStartX = startX;
        lineStartY = 0;
        lineEndX = startX;
        lineEndY = endY;
        centerX = startX;
        centerXInside =centerX;
        centerY = endY; //比例 1080p下游标饼半径为 80px
        centerYInside = centerY;
    }

    public void setIsCalibrationMode(boolean isCalibrationMode) {
        this.isCalibrationMode = isCalibrationMode;
    }

}
