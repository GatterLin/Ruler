package com.ruler.csw.activity;

import com.ruler.csw.R;
import com.ruler.csw.application.App;
import com.ruler.csw.base.BaseActivity;
import com.ruler.csw.databinding.ActivityMainBinding;
import com.ruler.csw.util.MySP;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class MainActivity extends BaseActivity {
    public ActivityMainBinding binding;
    // admob
//    private InterstitialAd mInterstitialAd;
//    public IAdWorker bannerAd;
//    private IAdWorker interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValue();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // admob
//        initAdMob();

//        initMiAd();
    }

    private void initValue() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        App.screenW = dm.widthPixels;
        App.screenH = dm.heightPixels;
        App.unit = (String) MySP.getInstance(this).getData("unit", "cm");
        App.size1mm = (float) MySP.getInstance(this).getData("size_1mm",
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, dm));
        App.size1_32inch = (float) MySP.getInstance(this).getData("size1_32inch",
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 1/32f, dm));
        App.size1px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1, dm);
        App.sizeRationMM = App.size1mm / App.size1px;
        App.sizeRationINCH = App.size1_32inch / App.size1px;
    }

    // admob
//    private void initAdMob() {
//        MobileAds.initialize(this, getResources().getString(R.string.app_ad_unit_id));
//
//        FrameLayout.LayoutParams adParams = (FrameLayout.LayoutParams) binding.adView.getLayoutParams();
//        adParams.gravity = Gravity.START;
//        adParams.topMargin = (int)(App.screenH / 20f * 14); //比例
//        binding.adView.setLayoutParams(adParams);
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        binding.adView.loadAd(adRequest);
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inset_ad_unit_id));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//    }

//    private void initMiAd() {
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.bannerContainer.getLayoutParams();
//        params.gravity = Gravity.START;
//        params.topMargin = (int)(App.screenH / 20f * 14); //比例
//        binding.bannerContainer.setLayoutParams(params);
//
//        try {
//            bannerAd = AdWorkerFactory.getAdWorker(this, binding.bannerContainer, new MimoAdListener() {
//                @Override
//                public void onAdPresent() {
//
//                }
//
//                @Override
//                public void onAdClick() {
//
//                }
//
//                @Override
//                public void onAdDismissed() {
//
//                }
//
//                @Override
//                public void onAdFailed(String s) {
//
//                }
//
//                @Override
//                public void onAdLoaded(int i) {
//
//                }
//
//                @Override
//                public void onStimulateSuccess() {
//
//                }
//            }, AdType.AD_BANNER);
//            binding.bannerContainer.setVisibility(View.VISIBLE);
//            bannerAd.loadAndShow(App.MiAd.BannerId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            interstitialAd = AdWorkerFactory.getAdWorker(this, (ViewGroup) getWindow().getDecorView(),
//                    new MimoAdListener() {
//                        @Override
//                        public void onAdPresent() {
//
//                        }
//
//                        @Override
//                        public void onAdClick() {
//
//                        }
//
//                        @Override
//                        public void onAdDismissed() {
//
//                        }
//
//                        @Override
//                        public void onAdFailed(String s) {
//
//                        }
//
//                        @Override
//                        public void onAdLoaded(int i) {
//
//                        }
//
//                        @Override
//                        public void onStimulateSuccess() {
//
//                        }
//                    }, AdType.AD_INTERSTITIAL);
//            interstitialAd.load(App.MiAd.InterstitialAd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (interstitialAd.isReady())
//                interstitialAd.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            if (bannerAd != null) {
//                bannerAd.recycle();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
