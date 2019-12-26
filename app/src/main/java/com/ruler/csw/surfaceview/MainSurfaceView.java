package com.ruler.csw.surfaceview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ruler.csw.baseview.BaseSurfaceView;
import com.ruler.csw.global.SizeInfoHandler;

/**
 * Created by 丛 on 2018/6/13 0013.
 */
public class MainSurfaceView extends BaseSurfaceView implements SizeInfoHandler {
    private final int BgColor = Color.parseColor("#EDEDED");

    // 绘制类
    public ScaleView scaleView;
    public CursorView cursorView;
    public LengthTextView lengthTextView;
    public SettingView settingView;

    public MainSurfaceView(Context context) {
        super(context);
        init();
    }

    public MainSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        scaleView = new ScaleView();
        if ("cm".equals(getCurUnit())) {
            cursorView = new CursorView((Activity) getContext(), getSize1mm() * 60f, getScreenH() / 2f);
        } else if ("inch".equals(getCurUnit())) {
            cursorView = new CursorView((Activity) getContext(), getSize1_32inch() * 32f, getScreenH() / 2f);
        }
        lengthTextView = new LengthTextView();
        settingView = new SettingView(this);
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint) {
        // 画背景
        canvas.drawColor(BgColor);
        // 画刻度
        scaleView.draw(canvas, paint);
        // 话游标
        cursorView.draw(canvas, paint);
        // 画距离文本
        lengthTextView.draw(canvas, paint);
        // 画设置
        settingView.draw(canvas, paint);
    }

    @Override
    public void logic() {
        scaleView.logic();
        cursorView.logic();
        lengthTextView.logic(cursorView.getLengthString());
        settingView.logic();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (settingView.touch(event))
            return true;
        cursorView.touch(event);
        return true;
    }

}
