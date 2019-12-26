package com.ruler.csw.global;

/**
 * Created by 丛 on 2019/12/23 0023.
 */
public interface SizeInfoHandler {

    default float getScreenW() {
        return SizeInfo.getInst().getScreenWidth();
    }

    default float getScreenH() {
        return SizeInfo.getInst().getScreenHeight();
    }

    default float getSize1mm() {
        return SizeInfo.getInst().getSize1mm();
    }

    default float getSize1_32inch() {
        return SizeInfo.getInst().getSize1_32inch();
    }

    default float getSize1px() {
        return SizeInfo.getInst().getSize1px();
    }

    default float getSizeRationMM() {
        return SizeInfo.getInst().getSizeRationMM();
    }

    default float getSizeRationINCH() {
        return SizeInfo.getInst().getSizeRationINCH();
    }

    default String getCurUnit() {
        return SizeInfo.getInst().getCurUnit();
    }


    default SizeInfo setScreenW(float screenW) {
        return SizeInfo.getInst().setScreenWidth(screenW);
    }

    default SizeInfo setScreenH(float screenH) {
        return SizeInfo.getInst().setScreenHeight(screenH);
    }

    default SizeInfo setSize1mm(float size1mm) {
        return SizeInfo.getInst().setSize1mm(size1mm);
    }

    default SizeInfo setSize1_32inch(float size1_32inch) {
        return SizeInfo.getInst().setSize1_32inch(size1_32inch);
    }

    default SizeInfo setSize1px(float size1px) {
        return SizeInfo.getInst().setSize1px(size1px);
    }

    default SizeInfo setSizeRationMM(float sizeRationMM) {
        return SizeInfo.getInst().setSizeRationMM(sizeRationMM);
    }

    default SizeInfo setSizeRationINCH(float sizeRationINCH) {
        return SizeInfo.getInst().setSizeRationINCH(sizeRationINCH);
    }

    default SizeInfo setCurUnit(String curUnit) {
        return SizeInfo.getInst().setCurUnit(curUnit);
    }

}
