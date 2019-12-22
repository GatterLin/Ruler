package com.ruler.csw.util;

import android.content.Context;
import android.util.Log;

import com.ruler.csw.application.App;
import com.ruler.csw.bean.Item;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丛 on 2017/9/10 0010.
 */

public class RecordUtil {
    private static final String fileName = "recorderList.txt"; // 头部是最新记录

    //点击确认存,修改描述信息后存,删除后存
    public static void saveRecorderList(Context context, List<Item> recorderList) {
        try {
            if (recorderList.size() > 100) {
                for (int i = 100; i < recorderList.size(); i++) {
                    recorderList.remove(i);
                }
            }

            File file = new File(context.getFilesDir(),fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recorderList);
            objectOutputStream.flush();

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //只在onCreate()第一次打开App读取读取
    public static List<Item> getRecorderList(Context context) {
        try {
            File file = new File(context.getFilesDir(), fileName);

            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Item> recorderList = (List<Item>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

            return recorderList;
        } catch (Exception e) {
            e.printStackTrace();
            //异常说明recorderListData.txt中没有recorderListData对象,
            //所以可能是第一次启动App,new ArrayList对象并返回
            return new ArrayList<>();
        }

    }
}
