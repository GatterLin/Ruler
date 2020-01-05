package com.ruler.csw.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.ruler.csw.BuildConfig;

/**
 * Created by 丛 on 2020/1/6 0006.
 */
public class ClipboardUtil {

    public static boolean copy(Context context, String text) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) context.getApplicationContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("length", text);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.i("tag", e.toString());
            }
            return false;
        }
    }

}
