package com.ruler.csw.activity;

import com.ruler.csw.R;
import com.ruler.csw.baseview.BaseActivity;
import com.ruler.csw.databinding.ActivityMainBinding;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

public class MainActivity extends BaseActivity {
    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected boolean onBackBtnClick() {
        boolean b = binding.surfaceViewMain.settingView.isSettingOpen();
        if (b) {
            binding.surfaceViewMain.settingView.openOrCloseSetting(true);
            return true;
        }
        return false;
    }

}
