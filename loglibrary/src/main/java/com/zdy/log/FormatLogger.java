package com.zdy.log;

import android.content.Context;
import com.zdy.log.executor.LogExecutorService;
import com.zdy.log.mmap.MmapBufferLogger;
import com.zdy.log.server.LogStrategy;
import com.zdy.log.server.impls.MLogStrategy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建日期：2020/9/7 on 22:24
 * 描述：格式化log打印
 * 作者：zhudongyong
 */
public class FormatLogger implements LogStrategy {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");//日期格式;
    private static final String SPACE = " ";
    private final LogStrategy logStrategy;
    private Date date;

    public FormatLogger(Builder builder){
        logStrategy = builder.logStrategy;
        date = new Date();
        initNativeLogger(builder.context);
    }

    /**
     * 创建mmap文件
     */
    private void initNativeLogger(Context context) {
        MmapBufferLogger.getInstance()
                .init(context,LogConfig.getInstance().getMaxLogSizeMb(),LogConfig.getInstance().getMaxKeepDaily());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 格式化日志
     * @param priority
     * @param module
     * @param msg
     */
    @Override
    public void log(int priority,String module,String tag,String msg) {
        printLog(priority,module,tag,msg);
    }

    /**
     * 磁盘存储
     */
    @Override
    public void flush() {
        LogExecutorService.getLogExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                MmapBufferLogger.getInstance().flush();
            }
        });
    }

    /**
     * 打印日志
     * @param priority
     * @param module
     * @param tag
     * @param msg
     */
    private synchronized void printLog(int priority, final String module, final String tag,String msg){
        logStrategy.log(priority,module,tag,msg);
        final String log = formatLogger(tag,msg);
        LogExecutorService.getLogExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                MmapBufferLogger.getInstance().writeToMemory(module,log.getBytes());
            }
        });
    }

    /**
     * 格式化日志文件
     * @param tag
     * @param msg
     * @return
     */
    private String formatLogger(String tag,String msg) {
        date.setTime(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        builder.append(dateFormat.format(date));
        builder.append(SPACE);
        builder.append(Thread.currentThread().getName());
        builder.append(SPACE);
        builder.append(tag);
        builder.append(SPACE);
        builder.append(msg);
        builder.append("\n");
        return builder.toString();

    }

    public static class Builder{
        private Context context;
        LogStrategy logStrategy;

        private Builder(){

        }

        public Builder setContext(Context context){
            this.context = context.getApplicationContext();
            return this;
        }

        public FormatLogger build(){
            if (null==logStrategy){
                logStrategy = new MLogStrategy();
            }
            return new FormatLogger(this);
        }

    }
}