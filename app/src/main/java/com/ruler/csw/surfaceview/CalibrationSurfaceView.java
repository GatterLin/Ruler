package com.ruler.csw.surfaceview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ruler.csw.R;
import com.ruler.csw.activity.CalibrationActivity;
import com.ruler.csw.baseview.BaseActivity;
import com.ruler.csw.baseview.BaseSurfaceView;
import com.ruler.csw.global.SizeInfo;
import com.ruler.csw.global.SizeInfoHandler;
import com.ruler.csw.util.MySP;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 丛 on 2018/6/16 0016.
 */
public class CalibrationSurfaceView extends BaseSurfaceView<CalibrationActivity>
        implements CalibrationActivity.NPChangedListener, SizeInfoHandler {
    public int measureMode;

    private String[] calibrationName = new String[]{
            "3 cm",
            getResources().getString(R.string.calibration_credit_card),
            "1 inch"
    };

    private CursorView cursorView;
    private Bitmap bmpOK;
    private float bmpOKX;
    private float bmpOKY;
    private Bitmap bmpRestore;
    private float bmpRestoreX;
    private float bmpRestoreY;
    private Bitmap bmpBack;
    private float bmpBackX;
    private float bmpBackY;
    private float bmpTempY;
    private int times;

    public CalibrationSurfaceView(Context context) {
        super(context);
        init();
    }

    public CalibrationSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        measureMode = 1;

        cursorView = new CursorView((Activity) getContext(), getSize1mm() * 54f, getScreenH() / 1.5f);
        cursorView.setIsCalibrationMode(true);

        getActivity().setNPChangedListener(this);

        bmpOK = BitmapFactory.decodeResource(getResources(), R.drawable.ok);
        bmpOK = createScaleBitmap(bmpOK);
        bmpRestore = BitmapFactory.decodeResource(getResources(), R.drawable.reset);
        bmpRestore = createScaleBitmap(bmpRestore);
        bmpBack = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        bmpBack = createScaleBitmap(bmpBack);

        bmpOKX = getScreenW() / 20f * 14f;
        bmpOKY = getScreenH() / 20f * 17f;
        bmpRestoreX = getScreenW() / 20f * 16f;
        bmpRestoreY = getScreenH() / 20f * 17f;
        bmpBackX = getScreenW() / 20f * 18f;
        bmpBackY = getScreenH() / 20f * 17f;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint) {
        //画背景
        canvas.drawColor(getContext().getResources().getColor(R.color.colorBg));
        //画确定按钮
        canvas.drawBitmap(bmpOK, bmpOKX, bmpOKY, paint);
        //画还原按钮
        canvas.drawBitmap(bmpRestore, bmpRestoreX, bmpRestoreY, paint);
        //画返回按钮
        canvas.drawBitmap(bmpBack, bmpBackX, bmpBackY, paint);
        // 画距离
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(getScreenW() / 384); //设置除“游标线”外的宽度为5px(1080p) 比例
        float lineStartX = cursorView.getLineStartX();
        float lineEndY = cursorView.getLineEndY();
        //左箭头
        canvas.drawLine(0, lineEndY / 2,
                getScreenW() / 64, lineEndY / 2 - getScreenW() / 96, paint); //比例 1080p下为30px和20px
        canvas.drawLine(0, lineEndY / 2,
                getScreenW() / 64, lineEndY / 2 + getScreenW() / 96, paint); //比例 1080p下为30px和20px
        //虚线
        int i = 0;
        while(i < lineStartX) {
            canvas.drawLine(i, lineEndY / 2,
                    i + getScreenW() / 192, lineEndY / 2, paint); //比例 1080p下 10px
            i += getScreenW() / 96;//比例 1080p下 20px
        }
        //右箭头
        canvas.drawLine(lineStartX, lineEndY / 2,
                lineStartX - getScreenW() / 64, lineEndY / 2 - getScreenW() / 96, paint); //比例 1080p下为30px和20px
        canvas.drawLine(lineStartX, lineEndY / 2,
                lineStartX - getScreenW() / 64, lineEndY / 2 + getScreenW() / 96, paint); //比例 1080p下为30px和20px
        //画3cm、短边、直径文字
        paint.setTextSize(getScreenW() / 36f); //比例 1080p下 30px
        paint.setShadowLayer(getScreenW() / 960, getScreenW() / 1920, getScreenW() / 1920, Color.GRAY);//比例1080p

        switch (measureMode) {
            case 0:
                canvas.drawText(calibrationName[0], lineStartX / 5, lineEndY / 2.1f, paint);
                break;
            case 1:
                canvas.drawText(calibrationName[1], lineStartX / 5, lineEndY / 2.1f, paint);
                break;
            case 2:
                canvas.drawText(calibrationName[2], lineStartX / 5, lineEndY / 2.1f, paint);
                break;
        }
        //画游标
        cursorView.draw(canvas, paint);
    }

    @Override
    public void logic() {
        cursorView.logic();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //region "确定修改"按钮
            if (Math.abs(event.getX() - (bmpOKX + bmpOK.getWidth() / 2f)) < bmpOK.getWidth() &&
                    Math.abs(event.getY() - (bmpOKY + bmpOK.getHeight() / 2f)) < bmpOK.getHeight()) {
                CursorView.cursorLock = true;
                times = 0;
                bmpTempY = bmpOKY;
                //按下动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bmpOKY += 2;
                        times++;
                        if (times == 10) {
                            bmpOKY = bmpTempY;
                            timer.cancel();
                        }
                    }
                }, 0, 10);
                showDialogConfirm();
                return true;
            }
            //endregion
            //region"还原修改"按钮
            if (Math.abs(event.getX() - (bmpRestoreX + bmpRestore.getWidth() / 2f)) < bmpRestore.getWidth() &&
                    Math.abs(event.getY() - (bmpRestoreY + bmpRestore.getHeight() / 2f)) < bmpRestore.getHeight()) {
                CursorView.cursorLock = true;
                times = 0;
                bmpTempY = bmpRestoreY;
                //动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bmpRestoreY += 2;
                        times++;
                        if (times == 10) {
                            bmpRestoreY = bmpTempY;
                            timer.cancel();
                        }
                    }
                }, 0, 10);
                showDialogRestore();
                return true;
            }
            //endregion
            //region"返回"按钮
            if (Math.abs(event.getX() - (bmpBackX + bmpBack.getWidth() / 2f)) < bmpBack.getWidth() &&
                    Math.abs(event.getY() - (bmpBackY + bmpBack.getHeight() / 2f)) < bmpBack.getHeight()) {
                CursorView.cursorLock = true;
                times = 0;
                bmpTempY = bmpBackY;
                //动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bmpBackY += 2;
                        times++;
                        if (times == 10) {
                            bmpBackY = bmpTempY;
                            timer.cancel();
                        }
                    }
                }, 0, 10);
                ((BaseActivity) getContext()).finish();
                return true;
            }
            //endregion
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            CursorView.cursorLock = false;
        }
        cursorView.touch(event);
        return true;
    }

    @Override
    public void onNPValueChanged(int newValue) {
        measureMode = newValue;
        switch (newValue) {
            case 0:
                cursorView.setCursorPosition(getSize1mm() * 30f, getScreenH() / 1.5f); // 3cm
                break;
            case 1:
                cursorView.setCursorPosition(getSize1mm() * 54f, getScreenH() / 1.5f); // 银行卡短边
                break;
            case 2:
                cursorView.setCursorPosition(getSize1mm() * 25.4f, getScreenH() / 1.5f); // 1英寸
                break;
        }
    }

    private Bitmap createScaleBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap,
                (int) (getScreenW() / 20), (int) (getScreenW() / 20), false); //设置“设置”按钮尺寸为96*96(1080p) 比例
    }

    //提示"确认"对话框
    private void showDialogConfirm() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.save_calibration);
        builder.setMessage("");
        //builder.setMessage("提示内容");//设置内容
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                float measureLength = cursorView.getLineStartX();
                switch (measureMode) {
                    case 0: // 3cm
                        setSize1mm(measureLength / 30f);
                        setSizeRationMM(getSize1mm() / getSize1px());
                        setSize1_32inch(getSize1mm() * 25.4f / 32f);
                        setSizeRationINCH(getSize1_32inch() / getSize1px());
                        break;
                    case 1: // 银行卡短边
                        setSize1mm(measureLength / 54f);
                        setSizeRationMM(getSize1mm() / getSize1px());
                        setSize1_32inch(measureLength / 2.125f / 32f);
                        setSizeRationINCH(getSize1_32inch() / getSize1px());
                        break;
                    case 2: // 1inch
                        setSize1mm(measureLength / 25.4f);
                        setSizeRationMM(getSize1mm() / getSize1px());
                        setSize1_32inch(measureLength / 32f);
                        setSizeRationINCH(getSize1_32inch() / getSize1px());
                        break;
                }
                MySP.getInst(getContext()).saveData("size_1mm", getSize1mm());
                MySP.getInst(getContext()).saveData("size1_32inch", getSize1_32inch());
                Toast.makeText(getContext(), R.string.toast_save_success, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //提示"还原"对话框
    private void showDialogRestore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.reset_calibration);
        builder.setMessage("");
        //builder.setMessage("提示内容");//设置内容
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DisplayMetrics dm = getContext().getResources().getDisplayMetrics();

                setSize1mm(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, dm));
                MySP.getInst(getContext()).saveData("size_1mm", getSize1mm());
                setSizeRationMM(getSize1mm() / getSize1px());

                setSize1_32inch(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 1/32f, dm));
                MySP.getInst(getContext()).saveData("size1_32inch", getSize1_32inch());
                setSizeRationINCH(getSize1_32inch() / getSize1px());

                //还原numberPicker模式和模式变量
                ((CalibrationActivity) getContext()).binding.npCalibration.setValue(1);
                measureMode = 1;
                cursorView.setCursorPosition(getSize1mm() * 54f, getScreenH() / 1.5f);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
