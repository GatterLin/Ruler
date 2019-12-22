package com.ruler.csw.util;

import android.graphics.drawable.ColorDrawable;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * Created by 丛 on 2018/6/17 0017.
 */
public class NPSetter {
    /**
     * 设置NumberPicker分割线颜色,反射方法
     *
     * @param numberPicker：NumberPicker
     * @param color：int
     */
    public static void setDividerColor(NumberPicker numberPicker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field SelectionDividerField : pickerFields) {
            if (SelectionDividerField.getName().equals("mSelectionDivider")) {
                SelectionDividerField.setAccessible(true);
                try {
                    SelectionDividerField.set(numberPicker, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
