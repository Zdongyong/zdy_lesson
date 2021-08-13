package com.zdy.log.mmap;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.zdy.log.util.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.BufferOverflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建日期：2020/9/8 on 11:23
 * 描述：
 * 作者：zhudongyong
 */
public class MmapBufferLogger {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");//日期格式;

    public static String FORMAT_LOG_PREFIX = ".log";
    public static String LOG_PATH = "/logs";
    public static String CACHE_PATH = "/cache.log";
    private static final long KB = 1024;
    private static final long MB = 1024 * KB;
    private static final long DEFAULT_CACHE_SIZE = 1 * KB;
    private static final long COVER_MS = 24 * 60 * 60 * 1000;

    public static MmapBufferLogger mmapLogger;
    private MappedByteBuffer mappedByteBuffer;
    private Context mContext;
    private double maxLogSizeMb;
    private int maxKeepDaily;
    private String basePath,cachePath,logPath;
    private Date date;

    public MmapBufferLogger(){}

    public static MmapBufferLogger getInstance(){
        if (null == mmapLogger){
            synchronized (MmapBufferLogger.class){
                if (null==mmapLogger){
                    mmapLogger = new MmapBufferLogger();
                }
            }
        }
        return mmapLogger;
    }

    /**
     *
     * @param context
     * @param maxLogSizeMb 存储大小
     * @param maxKeepDaily 最大存储天数
     */
    public void init(Context context,double maxLogSizeMb, int maxKeepDaily){
        this.mContext = context;
        this.maxLogSizeMb = maxLogSizeMb * MB;
        this.maxKeepDaily = maxKeepDaily;
        basePath = FileUtil.getFilePath(mContext) + LOG_PATH;
        cachePath = FileUtil.getFilePath(mContext) +CACHE_PATH;
        date = new Date();
    }


    /**
     * flush到磁盘
     */
    public void flush() {
        date.setTime(System.currentTimeMillis());
        String fileName = logPath + "/" + dateFormat.format(date) + FORMAT_LOG_PREFIX;//日志存储路径
        File cacheFile = new File(cachePath);
        if (!cacheFile.exists()) {
            return;
        }

        RandomAccessFile rafi = null;
        RandomAccessFile rafo = null;
        FileChannel fci = null;
        FileChannel fco = null;

        try {
            File logFile = FileUtil.createFile(fileName);
            rafi = new RandomAccessFile(cacheFile, "rw");
            rafo = new RandomAccessFile(logFile, "rw");

            fci = rafi.getChannel();
            fco = rafo.getChannel();

            long cacheSize = fci.size();
            long logSize = fco.size();

            MappedByteBuffer mbbi = fci.map(FileChannel.MapMode.READ_WRITE, 0, cacheSize);
            MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, logSize, cacheSize);
            for (int i = 0; i < cacheSize; i++) {
                mbbo.put(mbbi.get(i));
            }
            //解除内存映射
            unmap(mbbi);
            unmap(mbbo);
            //清空缓存文件
            FileWriter fileWriter = new FileWriter(cacheFile);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
            unmap(mappedByteBuffer);
            mappedByteBuffer = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fci) {
                    fci.close();
                }

                if (null != fco) {
                    fco.close();
                }

                if (null != rafi) {
                    rafi.close();
                }

                if (null != rafo) {
                    rafo.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    /**
     * 向mmap写入日志，建立映射关系
     * @param module
     * @param log
     */
    public void writeToMemory(String module,byte[] log){

        checkLogSizeAndDaily();

        logPath = basePath + "/" + module;

        MappedByteBuffer mbbi = getMappedByteBuffer();
        try {
            if (mbbi != null) {
                mbbi.put(log);
            }
        }catch (BufferOverflowException e) {

        } finally {
            flush();
        }
    }


    /**
     * 创建文件，建立映射关系
     * @return
     */
    private MappedByteBuffer getMappedByteBuffer(){
        if (null!=mappedByteBuffer){
            return mappedByteBuffer;
        }

        File cacheFile = FileUtil.createFile(cachePath);//映射文件
        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        try {
            randomAccessFile = new RandomAccessFile(cacheFile,"rw");//建立映射关系，添加读写权限
            fileChannel = randomAccessFile.getChannel();

            if (fileChannel.size()>0){//如果缓存大于0，先存一下
                flush();
            }

            MappedByteBuffer mbbi = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, DEFAULT_CACHE_SIZE);
            if (null != mbbi) {
                mappedByteBuffer = mbbi;
                return mbbi;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void checkLogSizeAndDaily(){
        if (TextUtils.isEmpty(basePath)){
            return;
        }
        File file = new File(basePath);
        if (file.exists()
                && FileUtil.getFileSizes(file) > maxLogSizeMb){
            if (file.isFile()) {
                FileUtil.deleteFile(basePath, maxKeepDaily * COVER_MS);
            } else {
                FileUtil.deleteDirectory(basePath, maxKeepDaily * COVER_MS);
            }
        }
    }


    /**
     * 解除文件与内存的映射关系
     * @param mbbi
     */
    private void unmap(MappedByteBuffer mbbi){
        if (mbbi == null) {
            return;
        }
        try {
            Class<?> clazz = Class.forName("sun.nio.ch.FileChannelImpl");
            Method m = clazz.getDeclaredMethod("unmap", MappedByteBuffer.class);
            m.setAccessible(true);
            m.invoke(null, mbbi);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}