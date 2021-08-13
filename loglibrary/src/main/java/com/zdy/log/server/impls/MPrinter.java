package com.zdy.log.server.impls;

import android.content.Context;
import android.util.Log;

import com.zdy.log.FormatLogger;
import com.zdy.log.server.Logger;
import com.zdy.log.server.Printer;

/**
 * 创建日期：2020/9/7 on 19:46
 * 描述：
 * 作者：zhudongyong
 */
public class MPrinter implements Printer {

    private FormatLogger formatLogger;

    public MPrinter(Context context) {
        this.formatLogger = FormatLogger.newBuilder().setContext(context).build();
    }

    @Override
    public boolean isLoggable(int priority) {
        return priority > Logger.VERBOSE;
    }

    @Override
    public void log(int priority,String module, String tag, String msg) {
        formatLogger.log(priority,module,tag,msg);
    }

    @Override
    public void flush() {
        formatLogger.flush();
    }

}