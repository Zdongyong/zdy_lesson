package com.zdy.log.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建日期：2020/9/7 on 17:42
 * 描述：日志线程池
 * 作者：zhudongyong
 */
public class LogExecutorService {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1, new NameThreadFactory("moredian_log"));

    public static ExecutorService getLogExecutorService(){
        return executorService;
    }

    private static class NameThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NameThreadFactory(String namePrefix) {
            SecurityManager securityManager = System.getSecurityManager();
            this.group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix+"-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable var1) {
            Thread t = new Thread(this.group, var1, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }


}