package com.ruler.csw.global;

/**
 * Created by 丛 on 2019/12/23 0023.
 */
public class RulerInfo {

    private static RulerInfo instance = new RulerInfo();

    public static RulerInfo getInst() {
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

    private String rulerDirection;

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

    public String getRulerDirection() {
        return rulerDirection;
    }

    public RulerInfo setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
        return this;
    }

    public RulerInfo setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
        return this;
    }

    public RulerInfo setSize1mm(float size1mm) {
        this.size1mm = size1mm;
        return this;
    }

    public RulerInfo setSize1_32inch(float size1_32inch) {
        this.size1_32inch = size1_32inch;
        return this;
    }

    public RulerInfo setSize1px(float size1px) {
        this.size1px = size1px;
        return this;
    }

    public RulerInfo setSizeRationMM(float sizeRationMM) {
        this.sizeRationMM = sizeRationMM;
        return this;
    }

    public RulerInfo setSizeRationINCH(float sizeRationINCH) {
        this.sizeRationINCH = sizeRationINCH;
        return this;
    }

    public RulerInfo setCurUnit(String curUnit) {
        this.curUnit = curUnit;
        return this;
    }

    public RulerInfo setRulerDirection(String rulerDirection) {
        this.rulerDirection = rulerDirection;
        return this;
    }
}
