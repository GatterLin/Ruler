package com.ruler.csw.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ruler.csw.R;
import com.ruler.csw.BR;
import com.ruler.csw.baseview.BaseActivity;
import com.ruler.csw.baseview.BaseRecyclerViewAdapter;
import com.ruler.csw.bean.InfoBean;
import com.ruler.csw.databinding.ActivityInfoBinding;
import com.ruler.csw.util.ResUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 丛 on 2018/10/28 0028.
 */
public class InfoActivity extends BaseActivity {
    private ActivityInfoBinding binding;
    private final String email = "congsw@foxmail.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info);
        binding.setInfoActivity(this);
        binding.tbInfo.setContentInsetsAbsolute(0, 0); // 去掉toolbar内部边距
        initRv();
    }

    public static void intentFor(Activity activity) {
        activity.startActivity(new Intent(activity, InfoActivity.class));
    }

    private void initRv() {
        RecyclerView rv = binding.rvInfo;
        rv.setLayoutManager(new LinearLayoutManager(this));
        BaseRecyclerViewAdapter<InfoBean> adapter = new BaseRecyclerViewAdapter<>(
                this, BR.InfoBean, R.layout.item_info);
        rv.setAdapter(adapter);
        adapter.addAllEnd(addBeans());
        adapter.setItemClickListener(pos -> {
            switch (pos) {
                case 0:
                    launchAppDetail();
                    break;
                case 1:
                    launchAppDetail();
                    break;
                case 2:
                    Intent intentEmail = new Intent(Intent.ACTION_SEND);
                    intentEmail.setType("message/rfc822");
                    intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{ email });
                    startActivity(intentEmail);
                    break;
                case 3:
                    Uri uri = Uri.parse("https://github.com/congshengwu/Ruler");
                    Intent intentGithub = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intentGithub);
                    break;
            }
        });
    }

    private List<InfoBean> addBeans() {
        List<InfoBean> beanList = new ArrayList<>();

        InfoBean bean1 = new InfoBean();
        bean1.setIconResId(R.drawable.update);
        bean1.setTitle(ResUtil.getString(this, R.string.check_the_update));
        String verName = "";
        try {
            verName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            verName = "v" + verName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        bean1.setText(verName);

        InfoBean bean2 = new InfoBean();
        bean2.setIconResId(R.drawable.comment);
        bean2.setTitle(ResUtil.getString(this, R.string.rate_app));
        bean2.setText("");

        InfoBean bean3 = new InfoBean();
        bean3.setIconResId(R.drawable.e_mail);
        bean3.setTitle(ResUtil.getString(this, R.string.contact_developer));
        bean3.setText(email);

        InfoBean bean4 = new InfoBean();
        bean4.setIconResId(R.drawable.github);
        bean4.setTitle(ResUtil.getString(this, R.string.github_open_source));

        beanList.add(bean1);
        beanList.add(bean2);
        beanList.add(bean3);
        beanList.add(bean4);
        return beanList;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        }
    }

    private void launchAppDetail() {
        try{
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(InfoActivity.this, "您的手机没有安装安卓应用市场", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
