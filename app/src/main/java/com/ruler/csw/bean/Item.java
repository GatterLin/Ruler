package com.ruler.csw.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.ruler.csw.BR;
import com.ruler.csw.application.App;

import java.util.Calendar;

/**
 * Created by 丛 on 2017/9/10 0010.
 */

public class Item extends BaseObservable implements java.io.Serializable {
    private static final long serialVersionUID = 7171648896382358420L;

    private String length;
    private String timeStamp;
    private String description;
    private boolean isChecked;

    public Item(String length, String timeStamp, String description) {
        this.length = length;
        this.timeStamp = timeStamp;
        this.description = description;
        isChecked = false;
    }

    @Bindable
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
        notifyPropertyChanged(com.ruler.csw.BR.length);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(com.ruler.csw.BR.description);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getShowDate() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.valueOf(getTimeStamp()));
        int month = c.get(Calendar.MONTH); //月是从0开始的,要加1
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String show = "";
        if (App.language.equals("zh")) { // 中文
            show = (month + 1) + "月" + day + "日" + "," +
                    (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
        } else { // 默认
            show = App.Month[month] + day + "," +
                    (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
        }
        return show;
    }

    @Bindable
    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
        notifyPropertyChanged(com.ruler.csw.BR.isChecked);
    }
}
