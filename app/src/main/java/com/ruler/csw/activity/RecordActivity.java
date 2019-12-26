package com.ruler.csw.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.ruler.csw.R;
import com.ruler.csw.adapter.RecordAdapter;
import com.ruler.csw.application.App;
import com.ruler.csw.baseview.BaseActivity;
import com.ruler.csw.bean.Item;
import com.ruler.csw.databinding.ActivityRecordBinding;
import com.ruler.csw.util.RecordUtil;
import com.ruler.csw.BR;

import java.util.List;
import java.util.Locale;

/**
 * Created by 丛 on 2018/6/15 0015.
 */
public class RecordActivity extends BaseActivity implements RecordAdapter.CardViewClickListener, View.OnClickListener {

    private ActivityRecordBinding binding;
    private RecordAdapter adapter;
    private boolean isInSelectMode;
    private int selectCount;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        initFontScale();

        Intent data = getIntent();
        if (data != null) {
            Bundle bundle = data.getBundleExtra("bundle");
            if (bundle != null) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

        List<Item> recordList = RecordUtil.getRecorderList(this);
        if (recordList.isEmpty()) {
            binding.textViewNull.setVisibility(View.VISIBLE);
        } else {
            binding.textViewNull.setVisibility(View.GONE);
        }
        isInSelectMode = false;
        binding.btnChooseAll.setOnClickListener(this);
        binding.btnDelete.setOnClickListener(this);
        initRecyclerView(recordList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Locale locale = getResources().getConfiguration().locale;
        App.language = locale.getLanguage();
        changeCount();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(App.Result_Code_Finish_Activity);
        }
        if (isInSelectMode) {
            isInSelectMode = false;
            setAllCheckedState(false);
            hideAnim(binding.layoutChoose);
            binding.layoutChoose.setVisibility(View.GONE);
            binding.btnChooseAll.setText(R.string.select_all);
            selectCount = 0;
            binding.textViewChooseCount.setText(selectCount + "");
        } else {
            finish();
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeCount();
    }

    public static void intentFor(Activity activity) {
        Intent intent = new Intent(activity, RecordActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("bundle", bundle);
        activity.startActivityForResult(intent, App.Request_Code_Start_Activity);
    }

    private void changeCount() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        if (screenWidth <= screenHeight) { // 竖屏
            layoutManager.setSpanCount(2);
        } else if (screenWidth >= screenHeight) { // 横屏
            layoutManager.setSpanCount(3);
        }
    }

    private void initRecyclerView(List<Item> recordList) {
        layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new RecordAdapter(this, BR.Item, R.layout.item);
        adapter.addAllEnd(recordList);
        adapter.setCardViewClickListener(this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        if (isInSelectMode) {
            if (adapter.getBeanList().get(position).getIsChecked()) {
                adapter.getBeanList().get(position).setIsChecked(false);
                selectCount--;
            } else {
                adapter.getBeanList().get(position).setIsChecked(true);
                selectCount++;
            }
            if (selectCount == adapter.getItemCount()) {
                binding.btnChooseAll.setText(R.string.deselect_all);
            } else if (selectCount == 0) {
                binding.btnChooseAll.setText(R.string.select_all);
                hideAnim(binding.layoutChoose);
                binding.layoutChoose.setVisibility(View.GONE);
                isInSelectMode = false;
            } else {
                binding.btnChooseAll.setText(R.string.select_all);
            }
            binding.textViewChooseCount.setText(selectCount + "");
        } else {
            showDialogRecorder(position);
        }
    }

    @Override
    public void onItemLongClick(int position) {
        isInSelectMode = !isInSelectMode;
        if (isInSelectMode) {
            adapter.getBeanList().get(position).setIsChecked(true);
            showAnim(binding.layoutChoose);
            binding.layoutChoose.setVisibility(View.VISIBLE);
            selectCount++;
        } else {
            setAllCheckedState(false);
            hideAnim(binding.layoutChoose);
            binding.layoutChoose.setVisibility(View.GONE);
            binding.btnChooseAll.setText(R.string.select_all);
            selectCount = 0;
        }
        binding.textViewChooseCount.setText(selectCount + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chooseAll:
                if (selectCount == adapter.getItemCount()) { // 变为全部没选中状态
                    setAllCheckedState(false);
                    binding.btnChooseAll.setText(R.string.select_all);
                    selectCount = 0;
                } else { // 变为全选状态
                    setAllCheckedState(true);
                    binding.btnChooseAll.setText(R.string.deselect_all);
                    selectCount = adapter.getItemCount();
                }
                binding.textViewChooseCount.setText(selectCount + "");
                break;
            case R.id.btn_delete:
                for (int pos = 0; pos < adapter.getItemCount(); pos++) {
                    if (adapter.getBeanList().get(pos).getIsChecked()) {
                        adapter.remove(pos);
                        pos = -1;
                    }
                }

                isInSelectMode = false;
                binding.btnChooseAll.setText(R.string.select_all);
                hideAnim(binding.layoutChoose);
                binding.layoutChoose.setVisibility(View.GONE);
                RecordUtil.saveRecorderList(RecordActivity.this, adapter.getBeanList());
                if (adapter.getBeanList().isEmpty()) {
                    binding.textViewNull.setVisibility(View.VISIBLE);
                }
                selectCount = 0;
                break;
        }
    }

    //初始化字体,不跟随系统字体大小
    private void initFontScale(){
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = 1f;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale*metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration,metrics);
    }

    private void setAllCheckedState(boolean getIsChecked) {
        for (int pos = 0; pos < adapter.getItemCount(); pos++) {
            adapter.getBeanList().get(pos).setIsChecked(getIsChecked);
        }
    }

    private void showAnim(View view) {
        Animation animation =
                AnimationUtils.loadAnimation(this, R.anim.record_layout_in_anim);
        view.setAnimation(animation);
        animation.start();
    }

    private void hideAnim(View view) {
        Animation animation =
                AnimationUtils.loadAnimation(this, R.anim.record_layout_out_anim);
        view.setAnimation(animation);
        animation.start();
    }

    public void showDialogRecorder(final int position) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogLayout = inflater.inflate(R.layout.dialog_recorder, null);
        final EditText editTextDescribe = dialogLayout.findViewById(R.id.editText_describe);

        String description = adapter.getBeanList().get(position).getDescription();
        if (!description.equals(getResources().getString(R.string.no_description))) {
            editTextDescribe.setText(description);
        }

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.input_the_description)
                .setView(dialogLayout)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editTextDescribe.getText().toString().equals("")) {
                            adapter.getBeanList().get(position).setDescription(
                                    editTextDescribe.getText().toString());
                        }
                        //修改描述信息后存,点击确认存
                        RecordUtil.saveRecorderList(RecordActivity.this, adapter.getBeanList());
                    }
                })
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .create();
        dialog.show();
    }

}
