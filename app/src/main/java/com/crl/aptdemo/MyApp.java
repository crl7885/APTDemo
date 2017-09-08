package com.crl.aptdemo;

import android.app.Application;

import com.crl.aptmanager.APTManager;

/**
 * Created by chairuilong on 2017/9/8.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        APTManager.init(this);
    }
}
