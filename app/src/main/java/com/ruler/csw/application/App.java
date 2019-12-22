package com.ruler.csw.application;

import android.app.Application;


/**
 * Created by 丛 on 2018/6/13 0013.
 */
public class App extends Application {
    public static float screenW;
    public static float screenH;
    public static float size1mm;
    public static float size1_32inch; // 1/32英寸
    public static float size1px;
    public static float sizeRationMM; // 1毫米与1像素的比值
    public static float sizeRationINCH;
    public static final String[] Month = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    public static String language; // 动态改变的值,不在这里初始化
    public static String unit;

    public static final int Request_Code_Start_Activity = 0;
    public static final int Result_Code_Finish_Activity = 100;

    public static class MiAd {
        // 测试
//        public static final String AppId = "2882303761517411490";
//        public static final String BannerId = "802e356f1726f9ff39c69308bfd6f06a";
//        public static final String InterstitialAd = "1d576761b7701d436f5a9253e7cf9572";
        // 正式
//        public static final String AppId = "2882303761517617227";
//        public static final String BannerId = "4157fe2662f7bd02dd2159469227fb71";
//        public static final String InterstitialAd = "581d55b7a130670a180324b41febf1d8";

    }

    @Override
    public void onCreate() {
        super.onCreate();
//        MimoSdk.init(this, MiAd.AppId, "fake_app_key", "fake_app_token");
//        MimoSdk.setDebugOn(); // 打开调试，输出调试信息
//        MimoSdk.setStageOn(); // 打开测试请求开关，请求测试广告
    }

}
