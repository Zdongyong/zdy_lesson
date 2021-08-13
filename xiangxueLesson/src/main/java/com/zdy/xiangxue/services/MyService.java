package com.zdy.xiangxue.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * 创建日期：2020/9/1 on 10:08
 * 描述：
 * 作者：zhudongyong
 */
public class MyService extends Service {


    private static final String TAG = MyService.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}