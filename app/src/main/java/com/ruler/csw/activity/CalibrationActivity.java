package com.ruler.csw.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.KeyEvent;

import com.ruler.csw.application.App;
import com.ruler.csw.baseview.BaseActivity;
import com.ruler.csw.databinding.ActivityCalibrationBinding;
import com.ruler.csw.R;
import com.ruler.csw.util.NPSetter;


/**
 * Created by 丛 on 2018/6/16 0016.
 */
public class CalibrationActivity extends BaseActivity {
    public ActivityCalibrationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calibration);
        binding.npCalibration.setMaxValue(2);
        binding.npCalibration.setMinValue(0);
        binding.npCalibration.setValue(1);
        binding.npCalibration.setDisplayedValues(new String[]{
                getResources().getString(R.string.calibration_cm),
                getResources().getString(R.string.calibration_credit_card),
                getResources().getString(R.string.calibration_inch)
        });
        binding.npCalibration.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (listener != null) {
                listener.onNPValueChanged(newVal);
            }
        });
        NPSetter.setDividerColor(binding.npCalibration, getResources().getColor(R.color.colorTrans));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(App.Result_Code_Finish_Activity);
        }
        return super.onKeyDown(keyCode, event);
    }

    private NPChangedListener listener;

    public void setNPChangedListener(NPChangedListener listener) {
        this.listener = listener;
    }

    public interface NPChangedListener {
        void onNPValueChanged(int newValue);
    }

}
