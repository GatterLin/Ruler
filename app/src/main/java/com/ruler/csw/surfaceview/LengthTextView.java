package com.ruler.csw.surfaceview;

import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ruler.csw.BuildConfig;
import com.ruler.csw.R;
import com.ruler.csw.global.RulerInfoHandler;
import com.ruler.csw.util.ClipboardUtil;
import com.ruler.csw.util.DrawUtil;
import com.ruler.csw.util.ResUtil;

/**
 * Created by 丛 on 2018/6/14 0014.
 */
public class LengthTextView implements RulerInfoHandler {
    private Context appContext;
    private float textX;
    private float textY; // 字体的左下角坐标值
    private String lengthText;
    private Rect lengthTextRect;

    public LengthTextView(MainSurfaceView surfaceView) {
        appContext = surfaceView.getContext().getApplicationContext();
        textX = getScreenW() / 20f * 1f; // 距离字体绘制位置
        textY = getScreenH() / 20f * 19f; // 距离字体绘制位置
        lengthText = "";
        lengthTextRect = new Rect();
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(getScreenW() / 22f); // 设置显示距离字体大概100px(1080p) 比例

        // 绘制距离文本
        paint.setShadowLayer(getScreenW() / 960,
                getScreenW() / 1920, getScreenW() / 1920, Color.GRAY); // 比例1080p
        canvas.drawText(lengthText, textX, textY, paint);
        paint.setShadowLayer(0, 0, 0, Color.WHITE);

        // 计算文本的宽高
        DrawUtil.measureText(lengthText, paint, lengthTextRect);
    }

    public void logic(String lengthString) {
        this.lengthText = lengthString;
    }

    public void touch(MotionEvent event) {
        float moveX = event.getX();
        float moveY = event.getY();
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            if (moveX >= textX && moveX <= textX + lengthTextRect.width()
                    && moveY <= textY && moveY >= textY - lengthTextRect.height()) {
                boolean b = ClipboardUtil.copy(appContext, lengthText);
                if (b) {
                    Toast.makeText(appContext,
                            ResUtil.getString(appContext, R.string.toast_length_copy_clipboard),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
