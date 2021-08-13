package com.zdy.log.util;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 创建日期：2020/9/8 on 11:04
 * 描述：
 * 作者：zhudongyong
 */
public class FileUtil {


    /**
     * 文件大小
     * @param f
     * @return
     */
    public static long getFileSizes(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        if (flist == null) {
            return 0;
        }
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size += getFileSizes(flist[i]);
                } else {
                    size += getFileSize(flist[i]);
                }
            }
        }
        return size;
    }

    private static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    /**
     * 删除文件
     * @param fileName
     * @param intervalTime
     */
    public static void deleteFile(String fileName, long intervalTime) {
        File file = new File(fileName);
        if (!file.exists()){
            return;
        }
        if (!file.exists()){
            return;
        }
        String name = file.getName();
        if (name != null && name.endsWith("txt")) {
            long ltime = file.lastModified();
            if (System.currentTimeMillis() - ltime > intervalTime) {
                // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 删除文件夹
     * @param dir
     * @param intervalTime
     */
    public static void deleteDirectory(String dir, long intervalTime) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return;
        }
//        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                deleteFile(files[i].getAbsolutePath(), intervalTime);
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                deleteDirectory(files[i].getAbsolutePath(), intervalTime);
            }
        }
    }

    /**
     * 获得文件存储路径
     *
     * /storage/emulated/0/Android/data/{packageId}/files/Logs/log_2016-03-14.log
     *
     * @return
     */
    public static String getFilePath(Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            return context.getExternalFilesDir(null).getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/{packageId}/files/Logs/log_2016-03-14_16-15-09.log
        } else {
            return context.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }

    }


    /**
     * 创建文件
     * @param fileName
     */
    public static File createFile(String fileName){
        File file = new File(fileName);
        if (!file.exists()){
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}