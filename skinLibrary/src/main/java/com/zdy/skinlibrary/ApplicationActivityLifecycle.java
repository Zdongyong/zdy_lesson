package com.zdy.skinlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.zdy.skinlibrary.utils.SkinThemeUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import androidx.core.view.LayoutInflaterCompat;

/**
 * 创建日期：2020/7/10 on 21:58
 * 描述：管理activity的生命周期  设置观察者后与activity绑定  activity对应改变
 * 作者：zhudongyong
 */
public class ApplicationActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private Observable mObservable;//设置观察者
    private Map<Activity,SkinLayoutInflaterFactory> mLayoutInflaterFactories = new HashMap<>();


    public ApplicationActivityLifecycle(Observable observable){
        mObservable = observable;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

        //更新状态栏
        SkinThemeUtils.updateStatusBarColor(activity);

        /**
         *  更新布局视图
         */
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        try {
            //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
            //如设置过抛出一次
            //设置 mFactorySet 标签为false
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater,false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //使用factory2 设置布局加载工程
        SkinLayoutInflaterFactory skinLayoutInflaterFactory = new SkinLayoutInflaterFactory
                (activity);
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutInflaterFactory);
        mLayoutInflaterFactories.put(activity, skinLayoutInflaterFactory);

        mObservable.addObserver(skinLayoutInflaterFactory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        SkinLayoutInflaterFactory o = mLayoutInflaterFactories.remove(activity);
        SkinManager.getInstance().deleteObserver(o);
    }
}