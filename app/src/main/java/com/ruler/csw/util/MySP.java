package com.ruler.csw.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 丛 on 2018/6/16 0016.
 */
public class MySP {

    private static final String FILE_NAME="RulerConfiguration";
    private static SharedPreferences mySharedPreference;
    private static MySP instance;
    private SharedPreferences.Editor editor;

    private MySP(Context context) {
        mySharedPreference = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = mySharedPreference.edit();
    }

    public static synchronized MySP getInstance(Context context) {
        if(instance == null){
            instance = new MySP(context);
        }
        return instance;
    }

    /**
     * 根据userId的不同,保存不同的状态变量,所有用户的全局状态变量用key_all来表示
     * @param key
     * @param data
     */
    public void saveData(String key, Object data) {
        String type = data.getClass().getSimpleName();
        if ("Integer".equals(type)){
            editor.putInt(key, (Integer)data);
        }else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean) data);
        }else if ("String".equals(type)){
            editor.putString(key, (String) data);
        }else if ("Float".equals(type)){
            editor.putFloat(key, (Float) data);
        }else if ("Long".equals(type)){
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    public Object getData(String key, Object defaultValue) {
        String type = defaultValue.getClass().getSimpleName();
        if ("Integer".equals(type)){
            return mySharedPreference.getInt(key, (Integer) defaultValue);
        }else if("Boolean".equals(type)){
            return mySharedPreference.getBoolean(key, (Boolean) defaultValue);
        }else if ("String".equals(type)){
            return mySharedPreference.getString(key, (String) defaultValue);
        }else if("Float".equals(type)){
            return mySharedPreference.getFloat(key, (Float) defaultValue);
        }else if ("Long".equals(type)){
            return mySharedPreference.getLong(key, (Long) defaultValue);
        }
        return null;
    }

    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    public void clearData() {
        editor.clear();
        editor.apply();
    }

}
