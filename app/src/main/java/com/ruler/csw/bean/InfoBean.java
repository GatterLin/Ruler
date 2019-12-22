package com.ruler.csw.bean;

/**
 * Created by 丛 on 2018/10/28 0028.
 */
public class InfoBean {
    private int iconResId;
    private String title;
    private String text;

    public int getIconResId() {
        int a = com.ruler.csw.BR.length;
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
