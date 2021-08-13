package com.zdy.xiangxue.lesson03_proxy.HookActivity;

import android.app.Activity;
import android.app.Instrumentation;

import java.lang.reflect.Field;

/**
 * 创建日期：2020/9/4 on 15:19
 * 描述：
 * 作者：zhudongyong
 */
public class HookActivityUtil {

    public static void replaceInstrumentation(Activity activity){
        Class<?> clazz = activity.getClass();
        try {
            //通过Activity.class 拿到 mInstrumentation字段
            Field field = clazz.getDeclaredField("mInstrumentation");
            field.setAccessible(true);//通过反射获取私有变量的值

            //根据activity内mInstrumentation字段 获取Instrumentation对象
            Instrumentation instrumentation = (Instrumentation) field.get(activity);
            ////创建代理对象
            ProxyInstrumentation activityProxyInstrumentation = new ProxyInstrumentation(instrumentation);
            //替换
            field.set(activity,activityProxyInstrumentation);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}