package com.zdy.log.server.impls;

import android.content.Context;
import android.text.TextUtils;

import com.zdy.log.server.Logger;
import com.zdy.log.server.Printer;

import androidx.annotation.NonNull;

/**
 * 创建日期：2020/9/7 on 17:28
 * 描述：
 * 作者：zhudongyong
 */
public class MLogger implements Logger {

    private Printer mPrinter;

    public MLogger(@NonNull Context context){
        mPrinter = new MPrinter(context);
    }

    @Override
    public void v(String module, String tag, String msg) {
        log(VERBOSE,module,tag,msg);
    }

    @Override
    public void d(String module, String tag, String msg) {
        log(DEBUG,module,tag,msg);
    }

    @Override
    public void i(String module, String tag, String msg) {
        log(INFO,module,tag,msg);
    }

    @Override
    public void w(String module, String tag, String msg) {
        log(WARN,module,tag,msg);
    }

    @Override
    public void e(String module, String tag, String msg) {
        log(ERROR,module,tag,msg);
    }

    @Override
    public void a(String module, String tag, String msg) {
        log(ASSERT,module,tag,msg);
    }

    @Override
    public void flush() {
        mPrinter.flush();
    }


    private void log(int priority, String module, String tag, String msg) {
        if (TextUtils.isEmpty(module)){
            throw new RuntimeException("module must not be null");
        }
        if (TextUtils.isEmpty(tag)){
            throw new RuntimeException("tag must not be null");
        }
        if (TextUtils.isEmpty(msg)){
            msg = "msg is null";
        }

        if (mPrinter.isLoggable(priority)){
            mPrinter.log(priority,module,tag,msg);
        }

    }
}