package com.ruler.csw.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 丛 on 2018/6/16 0016.
 */
public abstract class BaseSurfaceView<T extends BaseActivity> extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder sh;
    private Paint paint; //在util的GetTextWidth方法中需要paint对象
    private Thread thread;
    private boolean flag;
    private Canvas canvas;

    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        sh = getHolder();
        sh.addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }

    private void doDraw() {
        try {
            canvas = sh.lockCanvas();
            if (canvas != null) {
                draw(canvas, paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null)
                sh.unlockCanvasAndPost(canvas);
        }
    }

    public abstract void draw(@NonNull Canvas canvas, @NonNull Paint paint);

    public abstract void logic();

    @Override
    public void run() {
        while (flag) {
            doDraw();
            logic();
            try {
                Thread.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected T getActivity() {
        return (T) getContext();
    }
}
