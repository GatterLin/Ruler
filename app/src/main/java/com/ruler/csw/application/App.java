package com.ruler.csw.application;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.ruler.csw.constant.StringConst;
import com.ruler.csw.global.RulerInfo;
import com.ruler.csw.util.DimensionUtil;
import com.ruler.csw.util.MySP;

// todo 反转尺子
// todo 设置项图标下添加中文描述

/**
 * Created by 丛 on 2018/6/13 0013.
 */
public class App extends Application {
    public static final String[] Month = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    public static String language; // 动态改变的值,不在这里初始化

    public static final int Request_Code_Start_Activity = 0;
    public static final int Result_Code_Finish_Activity = 100;

    @Override
    public void onCreate() {
        super.onCreate();
        initSizeInfo();
    }

    private void initSizeInfo() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float screenWidth = dm.widthPixels;
        float screenHeight = dm.heightPixels;
        String curUnit = (String) MySP.getInst(this)
                .getData(StringConst.SP_KEY_UNIT, StringConst.SP_DEFAULT_UNIT);
        float size1mm = (float) MySP.getInst(this).getData(StringConst.SP_KEY_SIZE1MM,
                DimensionUtil.convertToPixel(this, TypedValue.COMPLEX_UNIT_MM, 1));
        float size1_32inch = (float) MySP.getInst(this).getData(StringConst.SP_KEY_1_32INCH,
                DimensionUtil.convertToPixel(this, TypedValue.COMPLEX_UNIT_IN, 1 / 32f));
        float size1px = DimensionUtil.convertToPixel(this, TypedValue.COMPLEX_UNIT_PX, 1);
        float sizeRationMM = size1mm / size1px;
        float sizeRationINCH = size1_32inch / size1px;

        RulerInfo.getInst()
                .setScreenWidth(screenWidth)
                .setScreenHeight(screenHeight)
                .setCurUnit(curUnit)
                .setSize1mm(size1mm)
                .setSize1_32inch(size1_32inch)
                .setSize1px(size1px)
                .setSizeRationMM(sizeRationMM)
                .setSizeRationINCH(sizeRationINCH);
    }

}
