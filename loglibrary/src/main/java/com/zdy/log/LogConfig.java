package com.zdy.log;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 创建日期：2020/9/7 on 16:40
 * 描述：日志配置
 * 作者：zhudongyong
 */
public class LogConfig {

    private static LogConfig instance;

    /**
     * 上下文(*必须)
     */
    private Context context;

    /**
     * 日志最大容量，默认70M
     */
    private double maxLogSizeMb;

    /**
     * 日志保存日期。默认近7天
     */
    private int maxKeepDaily;

    /**
     * 是否进行日志加密
     */
    private Boolean isEncrypt;

    /**
     * 日志保存路径
     */
    private String logPath;

    /**
     * 日志缓存路径
     */
    private String cachePath;

    private static void init(){
        if (null == instance){
            synchronized (LogConfig.class){
                instance = new LogConfig();
            }
        }
    }

    public static LogConfig getInstance(){
        return instance;
    }

    private LogConfig(){}

    public Context getContext() {
        return context;
    }

    public double getMaxLogSizeMb() {
        return maxLogSizeMb;
    }

    public int getMaxKeepDaily() {
        return maxKeepDaily;
    }

    public Boolean getEncrypt() {
        return isEncrypt;
    }

    public String getLogPath() {
        return logPath;
    }

    public String getCachePath() {
        return cachePath;
    }

    public static class LogBuilder{
        private Context mContext;
        private double maxLogSizeMb = 1024;//默认1G
        private int maxKeepDaily = 3;//默认3天
        private Boolean isEncrypt = false;
        private String mLogPath;
        private String mCachePath;

        public LogBuilder setContext(@NonNull Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public LogBuilder setMaxLogSizeMb(double maxLogSizeMb) {
            this.maxLogSizeMb = maxLogSizeMb;
            return this;
        }

        public LogBuilder setMaxKeepDaily(int maxKeepDaily) {
            this.maxKeepDaily = maxKeepDaily;
            return this;
        }

        public LogBuilder setEncrypt(Boolean encrypt) {
            this.isEncrypt = encrypt;
            return this;
        }

        public LogBuilder setLogPath(String mLogPath) {
            this.mLogPath = mLogPath;
            return this;
        }

        public LogBuilder setCachePath(String mCachePath) {
            this.mCachePath = mCachePath;
            return this;
        }

        public LogConfig builder(){
            if(mContext==null){
                throw new RuntimeException("the Context must not be null");
            }
            init();
            instance.context = mContext;
            instance.maxLogSizeMb= maxLogSizeMb;
            instance.maxKeepDaily = maxKeepDaily;
            instance.isEncrypt = isEncrypt;
            instance.logPath = mLogPath;
            instance.cachePath = mCachePath;
            MoreLog.init(mContext);
            return instance;
        }
    }

}