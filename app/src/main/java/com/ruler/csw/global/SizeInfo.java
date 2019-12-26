package com.ruler.csw.global;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by 丛 on 2019/12/23 0023.
 */
public class SizeInfo {

    private static SizeInfo instance = new SizeInfo();

    public static SizeInfo getInst() {
        return instance;
    }

    private float screenWidth;
    private float screenHeight;

    private float size1mm;
    private float size1_32inch; // 1/32英寸
    private float size1px;
    private float sizeRationMM; // 1毫米与1像素的比值
    private float sizeRationINCH;

    private String curUnit;

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getSize1mm() {
        return size1mm;
    }

    public float getSize1_32inch() {
        return size1_32inch;
    }

    public float getSize1px() {
        return size1px;
    }

    public float getSizeRationMM() {
        return sizeRationMM;
    }

    public float getSizeRationINCH() {
        return sizeRationINCH;
    }

    public String getCurUnit() {
        return curUnit;
    }


    public SizeInfo setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
        return this;
    }

    public SizeInfo setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
        return this;
    }

    public SizeInfo setSize1mm(float size1mm) {
        this.size1mm = size1mm;
        return this;
    }

    public SizeInfo setSize1_32inch(float size1_32inch) {
        this.size1_32inch = size1_32inch;
        return this;
    }

    public SizeInfo setSize1px(float size1px) {
        this.size1px = size1px;
        return this;
    }

    public SizeInfo setSizeRationMM(float sizeRationMM) {
        this.sizeRationMM = sizeRationMM;
        return this;
    }

    public SizeInfo setSizeRationINCH(float sizeRationINCH) {
        this.sizeRationINCH = sizeRationINCH;
        return this;
    }

    public SizeInfo setCurUnit(String curUnit) {
        this.curUnit = curUnit;
        return this;
    }
}
