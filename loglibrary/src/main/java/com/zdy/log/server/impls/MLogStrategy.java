package com.zdy.log.server.impls;

import android.util.Log;
import com.zdy.log.server.LogStrategy;

/**
 * 创建日期：2020/9/7 on 22:05
 * 描述：打印日志
 * 作者：zhudongyong
 */
public class MLogStrategy implements LogStrategy {

    @Override
    public void log(int priority,String module,String tag, String msg) {
        Log.println(priority,tag,msg);
    }

    @Override
    public void flush() {

    }
}