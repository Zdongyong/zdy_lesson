package com.zdy.log.server;

/**
 * 创建日期：2020/9/7 on 22:04
 * 描述：日志策略
 * 作者：zhudongyong
 */

public interface LogStrategy {

    /**
     * 打印log
     * @param priority
     * @param tag
     * @param msg
     */
    void log(int priority,String module,String tag,String msg);

    void flush();

}
