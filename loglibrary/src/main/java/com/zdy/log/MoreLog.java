package com.zdy.log;

import android.content.Context;

import com.zdy.log.server.Logger;
import com.zdy.log.server.impls.MLogger;

import androidx.annotation.NonNull;

/**
 * 创建日期：2020/9/7 on 17:29
 * 描述：对外log封装类
 * 作者：zhudongyong
 */
public class MoreLog {

    private static Logger mLogger;

    public static void init(@NonNull Context context){
        mLogger = new MLogger(context);
    }

    public static Logger v(@NonNull String module, @NonNull String tag, String msg){
        mLogger.v(module,tag,msg);
        return mLogger;
    }

    public static Logger d(@NonNull String module, @NonNull String tag, String msg){
        mLogger.d(module,tag,msg);
        return mLogger;
    }

    public static Logger i(@NonNull String module, @NonNull String tag, String msg){
        mLogger.i(module,tag,msg);
        return mLogger;
    }

    public static Logger w(@NonNull String module, @NonNull String tag, String msg){
        mLogger.w(module,tag,msg);
        return mLogger;
    }

    public static Logger e(@NonNull String module, @NonNull String tag, String msg){
        mLogger.e(module,tag,msg);
        return mLogger;
    }

    public static void memory(){
        mLogger.flush();
    }


}