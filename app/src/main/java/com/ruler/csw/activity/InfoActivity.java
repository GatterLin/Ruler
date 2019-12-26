package com.ruler.csw.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.didikee.donate.AlipayDonate;
import android.didikee.donate.WeiXinDonate;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.ruler.csw.R;
import com.ruler.csw.BR;
import com.ruler.csw.baseview.BaseActivity;
import com.ruler.csw.baseview.BaseRecyclerViewAdapter;
import com.ruler.csw.bean.InfoBean;
import com.ruler.csw.databinding.ActivityInfoBinding;

import java.io.File;
import java.io.InputStream;
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

                    break;
            }
        });
    }

    private List<InfoBean> addBeans() {
        List<InfoBean> beanList = new ArrayList<>();

        InfoBean bean1 = new InfoBean();
        bean1.setIconResId(R.drawable.update);
        bean1.setTitle("检查更新");
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
        bean2.setTitle("评价应用");
        bean2.setText("");

        InfoBean bean3 = new InfoBean();
        bean3.setIconResId(R.drawable.e_mail);
        bean3.setTitle("联系开发者");
        bean3.setText("congsw@foxmail.com");

        beanList.add(bean1);
        beanList.add(bean2);
        beanList.add(bean3);
        return beanList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteWxImg();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cv1) {
            donateAlipay("fkx066318qeujes3spyjy6c");
        } else if (id == R.id.cv2) {
            getRedPacket();
        } else if (id == R.id.cv3) {
            donateWeixin();
        } else if (id == R.id.iv_back) {
            finish();
        }
    }

    private void donateAlipay(String payCode) {
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(this);
        if (hasInstalledAlipayClient) {
            AlipayDonate.startAlipayClient(this, payCode);
        }
    }

    private void donateWeixin() {
        InputStream weixinQrIs = getResources().openRawResource(R.raw.wx_donate);
        String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "Pictures" + File.separator +
                "尺子" + File.separator + "wx_donate.jpg";
        WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
        WeiXinDonate.donateViaWeiXin(this, qrPath);
        Toast.makeText(this, "从相册选取二维码进行打赏", Toast.LENGTH_LONG).show();
    }

    private void deleteWxImg() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "Pictures" + File.separator +
                "尺子" + File.separator + "wx_donate.jpg";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
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

    private void getRedPacket() {
        try {
            ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (manager != null) {
                manager.setPrimaryClip(ClipData.newPlainText("RedPacket", "526608712"));
            }
            Uri uri = Uri.parse("alipayqr://platformapi/startapp");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(this, "已复制526608712", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
