package com.zdy.log.server;

/**
 * 创建日期：2020/9/7 on 17:51
 * 描述：用来打印log
 * 作者：zhudongyong
 */

public interface Printer {

    /**
     * 是否log登记
     */
    boolean isLoggable(int priority);

    /**
     * 打印
     */
    void log(int priority, String module, String tag, String msg);

    /**
     * 磁盘存储
     */
    void flush();

}
