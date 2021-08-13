package com.zdy.xiangxue.lesson03_proxy.HookActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 创建日期：2020/9/4 on 17:38
 * 描述：
 * 作者：zhudongyong
 */
public class HookAMSUtil {

    private static final String TAG = "HookAMSUtil";

    public static void hookActivityManager(){
        try {
            //1-找到hook对象ActivityManager
            Class<?> activityManager;
            final Object hookActivityManager;
            Method getServiceMethod;
            if (ifSdkOverIncluding26()) {
                activityManager = Class.forName("android.app.ActivityManager");
                getServiceMethod = activityManager.getDeclaredMethod("getService");
            } else {
                activityManager = Class.forName("android.app.ActivityManagerNative");
                getServiceMethod = activityManager.getDeclaredMethod("getDefault");
            }
            hookActivityManager = getServiceMethod.invoke(null);//OK，已经取得这个系统自己的AMS实例

            //2-创建自己的代理类对象
            Class<?> iActivityManager = Class.forName("android.app.IActivityManager");
            Object proxyIActivityManager = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManager}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.i(TAG, "invoke: ======="+method.getName());
                    if (method.getName().equals("startActivity")){
                        Log.i(TAG, "invoke: hook yes==============");
                    }
                    return method.invoke(hookActivityManager,args);
                }
            });
            Field hookIActivityManagerSingleton;
            if (ifSdkOverIncluding26()) {
                hookIActivityManagerSingleton = activityManager.getDeclaredField("IActivityManagerSingleton");
            } else {
                hookIActivityManagerSingleton = activityManager.getDeclaredField("gDefault");
            }
            hookIActivityManagerSingleton.setAccessible(true);

            Object oHookIActivityManagerSingleton = hookIActivityManagerSingleton.get(null);
            Class<?> hookSingleton = Class.forName("android.util.Singleton");//反射创建一个Singleton的class
            Field fMInstance = hookSingleton.getDeclaredField("mInstance");
            fMInstance.setAccessible(true);
            fMInstance.set(oHookIActivityManagerSingleton,proxyIActivityManager);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    //设备系统版本是不是大于等于26
    private static boolean ifSdkOverIncluding26() {
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 26 || SDK_INT == 26) {
            return true;
        } else {
            return false;
        }
    }


}