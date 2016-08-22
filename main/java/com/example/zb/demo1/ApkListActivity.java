package com.example.zb.demo1;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class ApkListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_list);
        initView();
    }

    void initView() {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(mListener);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getApkList();
                }
            }).start();
        }
    };

    void getApkList() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(0);
        Iterator<PackageInfo> iterator = list.iterator();
        List<PackageInfo> system = Lists.newArrayList();
        List<PackageInfo> systemUnremoveable = Lists.newArrayList();
        List<PackageInfo> others = Lists.newArrayList();
        while (iterator.hasNext()) {
            PackageInfo info = iterator.next();
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                others.add(info);
                continue;
            }
            system.add(info);
        }
        for (PackageInfo info : others) {
            Log.i("ApkListActivity", "other:" + info.packageName + ", " + info.applicationInfo.loadLabel(pm));
        }
        for (PackageInfo info : system) {
            Log.i("ApkListActivity", "system:" + info.packageName + ", " + info.applicationInfo.loadLabel(pm));
        }
    }
}
