package com.ruler.csw.baseview;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by 丛 on 2018/6/13 0013.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 带有返回功能的toolBar
     * @param toolBar toolBar对象
     * @param title 如果在xml中设置了标题,这里填写null即可,无需重复设置
     */
    public final void setToolBarWithBackButton(Toolbar toolBar, @Nullable String title) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (title != null)
            toolBar.setTitle(title);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToolBarBackFunc();
            }
        });
    }

    protected void customToolBarBackFunc() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (onBackBtnClick()) {
            return;
        }
        super.onBackPressed();
    }

    protected boolean onBackBtnClick() {
        return false;
    }

}
