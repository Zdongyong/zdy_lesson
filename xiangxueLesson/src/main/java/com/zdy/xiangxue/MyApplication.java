package com.zdy.xiangxue;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.zdy.skinlibrary.SkinManager;
import com.zdy.xiangxue.lesson03_proxy.HookActivity.HookAMSUtil;

/**
 * @author Lance
 * @date 2018/3/8
 */

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private static final String MAIN_PROCESS_NAME = "com.zdy.xiangxue";

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()){
            Log.i(TAG, "onCreate: MyApplication onCreate");
            SkinManager.init(this);
        }
    }

    /**
     * 由于开通了romote进程，所以需要判断是否是当前主线程
     * @return
     */
    private boolean isMainProcess(){
        int mainProcessId = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo runningProcessInfo: manager.getRunningAppProcesses()){
            if (runningProcessInfo.pid == mainProcessId
                    && runningProcessInfo.processName.equals(MAIN_PROCESS_NAME)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ReflectHelper.unseal(this);
    }
}
