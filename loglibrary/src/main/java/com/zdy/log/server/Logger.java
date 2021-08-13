package com.zdy.log.server;

/**
 * 创建日期：2020/9/7 on 16:35
 * 描述：日志接口-方法
 * module 文件名TAG
 * tag 文件tag
 * msg 文件日志消息
 * 作者：zhudongyong
 */

public interface Logger {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    void v(String module,String tag,String msg);

    void d(String module,String tag,String msg);

    void i(String module,String tag,String msg);

    void w(String module,String tag,String msg);

    void e(String module,String tag,String msg);

    void a(String module,String tag,String msg);

    void flush();

}
