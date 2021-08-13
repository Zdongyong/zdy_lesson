package com.zdy.xiangxue.lesson03_proxy.HookActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 创建日期：2020/9/4 on 15:29
 * 描述：
 * 作者：zhudongyong
 */
public class ProxyInstrumentation extends Instrumentation {


    private static final String TAG = ProxyInstrumentation.class.getSimpleName();
    private Instrumentation baseInstrumentation;//原始Instrumentation

    public ProxyInstrumentation(Instrumentation instrumentation) {
        baseInstrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Log.i(TAG, "execStartActivity: ==============");

        try {
//          开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
//          由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
            Method execStartActivity = Instrumentation.class.getMethod(
                    "execStartActivity",
                           Context.class,IBinder.class, IBinder.class, Activity.class,
                                Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(baseInstrumentation,who,contextThread,
                    token,target,intent,requestCode,options);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}