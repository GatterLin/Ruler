package com.ruler.csw.global;

/**
 * Created by 丛 on 2019/12/23 0023.
 */
public interface RulerInfoHandler {

    default float getScreenW() {
        return RulerInfo.getInst().getScreenWidth();
    }

    default float getScreenH() {
        return RulerInfo.getInst().getScreenHeight();
    }

    default float getSize1mm() {
        return RulerInfo.getInst().getSize1mm();
    }

    default float getSize1_32inch() {
        return RulerInfo.getInst().getSize1_32inch();
    }

    default float getSize1px() {
        return RulerInfo.getInst().getSize1px();
    }

    default float getSizeRationMM() {
        return RulerInfo.getInst().getSizeRationMM();
    }

    default float getSizeRationINCH() {
        return RulerInfo.getInst().getSizeRationINCH();
    }

    default String getCurUnit() {
        return RulerInfo.getInst().getCurUnit();
    }

    default String getRulerDirection() {
        return RulerInfo.getInst().getRulerDirection();
    }


    default RulerInfo setScreenW(float screenW) {
        return RulerInfo.getInst().setScreenWidth(screenW);
    }

    default RulerInfo setScreenH(float screenH) {
        return RulerInfo.getInst().setScreenHeight(screenH);
    }

    default RulerInfo setSize1mm(float size1mm) {
        return RulerInfo.getInst().setSize1mm(size1mm);
    }

    default RulerInfo setSize1_32inch(float size1_32inch) {
        return RulerInfo.getInst().setSize1_32inch(size1_32inch);
    }

    default RulerInfo setSize1px(float size1px) {
        return RulerInfo.getInst().setSize1px(size1px);
    }

    default RulerInfo setSizeRationMM(float sizeRationMM) {
        return RulerInfo.getInst().setSizeRationMM(sizeRationMM);
    }

    default RulerInfo setSizeRationINCH(float sizeRationINCH) {
        return RulerInfo.getInst().setSizeRationINCH(sizeRationINCH);
    }

    default RulerInfo setCurUnit(String curUnit) {
        return RulerInfo.getInst().setCurUnit(curUnit);
    }

    default RulerInfo setRulerDirection(String rulerDirection) {
        return RulerInfo.getInst().setRulerDirection(rulerDirection);
    }

}
