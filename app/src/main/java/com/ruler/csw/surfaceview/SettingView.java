package com.ruler.csw.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ruler.csw.R;
import com.ruler.csw.activity.CalibrationActivity;
import com.ruler.csw.activity.InfoActivity;
import com.ruler.csw.activity.MainActivity;
import com.ruler.csw.activity.RecordActivity;
import com.ruler.csw.application.App;
import com.ruler.csw.bean.Item;
import com.ruler.csw.global.SizeInfoHandler;
import com.ruler.csw.util.MySP;
import com.ruler.csw.util.RecordUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by 丛 on 2018/6/14 0014.
 */
public class SettingView implements SizeInfoHandler {
    private Bitmap bmpSetting;
    private Bitmap bmpSave;
    private Bitmap bmpRecorder;
    private Bitmap bmpUnit;
    private Bitmap bmpCalibration;
    private Bitmap bmpInfo;
    private float settingX, settingY;
    //    private float temp_settingIconY;
    private float saveX, saveY;
    private float recordX, recordY;
    private float unitX, unitY;
    private float calibrationX, calibrationY;
    private float infoX, infoY;
    private float bmpTempY;
    private int times;
    private float degree; //设置齿轮的旋转角度 0~180
    private float popLengthSetting; //在settingViewX的基础上向右加X值实现pop
    private float popLengthSave;
    private float popLengthRecorder;
    private float popLengthTurn;
    private float popLengthCalibration;
    private float popLengthInfo;
    private boolean isSettingOpen;

    private Timer timer;
    private MainSurfaceView surfaceView;
    private Context context;

    public SettingView(MainSurfaceView surfaceView) {
        this.surfaceView = surfaceView;
        this.context = surfaceView.getContext();
        Resources res = context.getResources();
        bmpSetting = BitmapFactory.decodeResource(res, R.drawable.setting);
        bmpSetting = createScaleBitmap(bmpSetting);
        bmpSave = BitmapFactory.decodeResource(res, R.drawable.save);
        bmpSave = createScaleBitmap(bmpSave);
        bmpRecorder = BitmapFactory.decodeResource(res, R.drawable.record);
        bmpRecorder = createScaleBitmap(bmpRecorder);
        bmpUnit = BitmapFactory.decodeResource(res,
                "cm".equals(getCurUnit()) ? R.drawable.unit_cm : R.drawable.unit_inch);
        bmpUnit = createScaleBitmap(bmpUnit);
        bmpCalibration = BitmapFactory.decodeResource(res, R.drawable.calibration);
        bmpCalibration = createScaleBitmap(bmpCalibration);
        bmpInfo = BitmapFactory.decodeResource(res, R.drawable.info);
        bmpInfo = createScaleBitmap(bmpInfo);

        settingX = getScreenW() / 20f * 18.5f; //"设置"图标绘制位置X
        settingY = getScreenH() / 20f * 17.5f; //"设置"图标绘制位置Y,这个值和numberpicker的Y坐标相关
        saveX = settingX;
        saveY = settingY;
        unitX = settingX;
        unitY = settingY;
        recordX = settingX;
        recordY = settingY;
        calibrationX = settingX;
        calibrationY = settingY;
        infoX =settingX;
        infoY = settingY;
        degree = 0;
        popLengthSetting = 0; // 弹出的距离, 不断增加或减少
        popLengthSave = 0;
        popLengthRecorder = 0;
        popLengthTurn = 0;
        popLengthCalibration = 0;
        popLengthInfo = 0;
        isSettingOpen = false;

    }

    public void draw(Canvas canvas, Paint paint) {
        if (degree == 0) {
            canvas.drawBitmap(bmpSetting, settingX, settingY, paint);
        } else {
            canvas.save();
            canvas.rotate(degree, settingX + bmpSetting.getWidth() / 2f,
                    settingY + bmpSetting.getHeight() / 2f);
            canvas.drawBitmap(bmpSetting, settingX, settingY, paint);
            canvas.restore();
            // 隐藏下面设置中的两个选项
            canvas.drawBitmap(bmpSave, saveX + popLengthSave, saveY, paint);
            canvas.drawBitmap(bmpRecorder, recordX + popLengthRecorder, recordY, paint);
            canvas.drawBitmap(bmpUnit, unitX + popLengthTurn, unitY, paint);
            canvas.drawBitmap(bmpCalibration, calibrationX + popLengthCalibration, calibrationY, paint);
            canvas.drawBitmap(bmpInfo, infoX + popLengthInfo, infoY, paint);
        }
    }

    public void logic() {

    }

    /**
     * @param event 上一级传来的点击事件
     * @return 点击成功返回true(点到设置,点到设置中的选项),否则返回false
     */
    public boolean touch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //region 点到设置图标
            if (Math.abs(event.getX() - (settingX + bmpSetting.getWidth() / 2f))
                    < bmpSetting.getWidth()
                    &&
                    Math.abs(event.getY() - (getScreenH() / 20f * 17.5f + bmpSetting.getHeight() / 2f))
                            < bmpSetting.getHeight()) {
                CursorView.cursorLock = true;
                openOrCloseSetting(isSettingOpen);

                return true;
            }
            //endregion
            //region 点到保存
            if (Math.abs(event.getX() - (saveX + bmpSave.getWidth() / 2f + popLengthSave)) //右侧括号里的是设置中心的坐标
                    < bmpSave.getWidth()
                    &&
                    Math.abs(event.getY() - (getScreenH() / 20f * 17.5f + bmpSave.getHeight() / 2f))
                            < bmpSave.getHeight()
                    &&
                    isSettingOpen) {
                CursorView.cursorLock = true;
                saveRecord();
                // 按下动画
                times = 0;
                bmpTempY = saveY;
                //动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        saveY += 2;
                        times++;
                        if (times == 10) {
                            saveY = bmpTempY;
                            timer.cancel();
                            openOrCloseSetting(isSettingOpen);
                        }
                    }
                }, 0, 10);
                return true;
            }
            //endregion
            //region 点到历史记录
            if (Math.abs(event.getX() - (recordX + bmpRecorder.getWidth() / 2f + popLengthRecorder)) //右侧括号里的是设置中心的坐标
                    < bmpRecorder.getWidth()
                    &&
                    Math.abs(event.getY() - (getScreenH() / 20f * 17.5f + bmpRecorder.getHeight() / 2f))
                            < bmpRecorder.getHeight()
                    &&
                    isSettingOpen) {
                CursorView.cursorLock = true;
                RecordActivity.intentFor((Activity) context);
                // 按下动画
                times = 0;
                bmpTempY = recordY;
                //动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        recordY += 2;
                        times++;
                        if (times == 10) {
                            recordY = bmpTempY;
                            timer.cancel();
                            openOrCloseSetting(isSettingOpen);
                        }
                    }
                }, 0, 10);
                return true;
            }
            //endregion
            //region 点击到切换单位
            if (Math.abs(event.getX() - (unitX + bmpUnit.getWidth() / 2f + popLengthTurn)) //右侧括号里的是设置中心的坐标
                    < bmpUnit.getWidth()
                    &&
                    Math.abs(event.getY() - (getScreenH() / 20f * 17.5f + bmpUnit.getHeight() / 2f))
                            < bmpUnit.getHeight()
                    &&
                    isSettingOpen) {
                CursorView.cursorLock = true;
                if ("cm".equals(getCurUnit())) {
                    setCurUnit("inch");
                    MySP.getInst(context).saveData("unit", "inch");
                    bmpUnit = BitmapFactory.decodeResource(context.getResources(), R.drawable.unit_inch);
                    bmpUnit = createScaleBitmap(bmpUnit);
                } else if ("inch".equals(getCurUnit())) {
                    setCurUnit("cm");
                    MySP.getInst(context).saveData("unit", "cm");
                    bmpUnit = BitmapFactory.decodeResource(context.getResources(), R.drawable.unit_cm);
                    bmpUnit = createScaleBitmap(bmpUnit);
                }
                // 按下动画
                times = 0;
                bmpTempY = unitY;
                //动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        unitY += 2;
                        times++;
                        if (times == 10) {
                            unitY = bmpTempY;
                            timer.cancel();
                            openOrCloseSetting(isSettingOpen);
                        }
                    }
                }, 0, 10);
                return true;
            }
            //endregion
            //region 点到校准
            if (Math.abs(event.getX() - (calibrationX + bmpCalibration.getWidth() / 2f + popLengthCalibration)) //右侧括号里的是设置中心的坐标
                    < bmpCalibration.getWidth()
                    &&
                    Math.abs(event.getY() - (getScreenH() / 20f * 17.5f + bmpCalibration.getHeight() / 2f))
                            < bmpCalibration.getHeight()
                    &&
                    isSettingOpen) {
                CursorView.cursorLock = true;
                Intent intent = new Intent(context, CalibrationActivity.class);
                ((MainActivity) context).startActivityForResult(intent, App.Request_Code_Start_Activity);
                Toast.makeText(context, R.string.calibration, Toast.LENGTH_SHORT).show();

                // 按下动画
                times = 0;
                bmpTempY = calibrationY;
                //动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        calibrationY += 2;
                        times++;
                        if (times == 10) {
                            calibrationY = bmpTempY;
                            timer.cancel();
                            openOrCloseSetting(isSettingOpen);
                        }
                    }
                }, 0, 10);
                return true;
            } else if (Math.abs(event.getX() - (infoX + bmpInfo.getWidth() / 2f + popLengthInfo)) //右侧括号里的是设置中心的坐标
                    < bmpInfo.getWidth()
                    &&
                    Math.abs(event.getY() - (getScreenH() / 20f * 17.5f + bmpInfo.getHeight() / 2f))
                            < bmpInfo.getHeight()
                    &&
                    isSettingOpen) {
                CursorView.cursorLock = true;
                InfoActivity.intentFor((Activity) context);

                // 按下动画
                times = 0;
                bmpTempY = infoY;
                //动画效果
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        infoY += 2;
                        times++;
                        if (times == 10) {
                            infoY = bmpTempY;
                            timer.cancel();
                            openOrCloseSetting(isSettingOpen);
                        }
                    }
                }, 0, 10);
                return true;
            }
            //endregion
            //region 点击屏幕其他区域,关闭弹出的设置
            if (isSettingOpen) {
                openOrCloseSetting(isSettingOpen);
                return false;
            }
            //endregion
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            CursorView.cursorLock = false;
        }
        return false;
    }

    private Bitmap createScaleBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap,
                (int) (getScreenW() / 20), (int) (getScreenW() / 20), false); //设置“设置”按钮尺寸为96*96(1080p) 比例
    }

    private void openOrCloseSetting(boolean isOpen) {
        if (timer != null)
            timer.cancel();
        timer = new Timer();
        if (isOpen) {
            // 隐藏admob
//            AdView adView = ((MainActivity) context).binding.adView;
//            adView.post(new Runnable() {
//                @Override
//                public void run() {
//                    adView.setVisibility(View.GONE);
//                }
//            });

            // 隐藏Mi广告
//            FrameLayout bannerContainer = ((MainActivity) context).binding.bannerContainer;
//            bannerContainer.post(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        bannerContainer.setVisibility(View.GONE);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    degree -= 1;
                    popLengthSave += getScreenW() / 1920; //1px(1080p) 比例
                    popLengthRecorder += getScreenW() / 960f; //2px(1080p) 比例
                    popLengthTurn += getScreenW() / 640f; //3px(1080p) 比例
                    popLengthCalibration += getScreenW() / 480f; //4px(1080p) 比例
                    popLengthInfo += getScreenW() / 384f; //5px(1080p) 比例

                    if (degree <= 0) {
                        isSettingOpen = false;
                        timer.cancel();
                    }
                }
            }, 0, 2); //每隔2ms执行一次, 180 * 3 = 540ms
        } else {
            // 显示admob
//            AdView adView = ((MainActivity) context).binding.adView;
//            adView.post(new Runnable() {
//                @Override
//                public void run() {
//                    adView.setVisibility(View.VISIBLE);
//                }
//            });

//            // 显示Mi广告
//            FrameLayout bannerContainer = ((MainActivity) context).binding.bannerContainer;
//            bannerContainer.post(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        if (MimoSdk.isSdkReady()) {
//                            bannerContainer.setVisibility(View.VISIBLE);
//                            ((MainActivity) context).bannerAd.loadAndShow(App.MiAd.BannerId);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

            isSettingOpen = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    degree += 1;
                    popLengthSave -= getScreenW() / 1920; //1px(1080p) 比例
                    popLengthRecorder -= getScreenW() / 960f; //2px(1080p) 比例
                    popLengthTurn -= getScreenW() / 640f; //3px(1080p) 比例
                    popLengthCalibration -= getScreenW() / 480f; //4px(1080p) 比例
                    popLengthInfo -= getScreenW() / 384f; //5px(1080p) 比例
                    if (degree >= 180) {
                        timer.cancel();
                    }
                }
            }, 0, 2); //每隔2ms执行一次, 180 * 3 = 540ms
        }
    }

    private void saveRecord() {
//        Item item = new Item(String.valueOf(length),
//                (month + 1) + "月" + day + "日" + " " + hour + ":" + minute,
//                "");
        Item item = new Item(surfaceView.cursorView.getLengthString(),
                System.currentTimeMillis() + "",
                "");

        List<Item> list = RecordUtil.getRecorderList(context);
        list.add(0, item);
        RecordUtil.saveRecorderList(context, list);
        // toast提示
        Toast.makeText(context, R.string.toast_save_success, Toast.LENGTH_SHORT).show();
    }
}
