package com.ruler.csw.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

/**
 * Created by 丛 on 2018/10/29 0029.
 */
public class DataBindingAdapter {

    @BindingAdapter("srcResId")
    public static void setImageViewSrc(ImageView iv, int resId) {
        iv.setImageResource(resId);
    }

}
