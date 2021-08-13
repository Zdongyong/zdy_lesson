package com.zdy.skinlibrary;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.zdy.skinlibrary.utils.SkinResources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

/**
 * 创建日期：2020/7/10 on 18:28
 * 描述：皮肤管理器 被观察者
 * 作者：zhudongyong
 */
public class SkinManager extends Observable {

    private volatile static SkinManager instance;
    private Context mContext;
    private ApplicationActivityLifecycle skinActivityLifecycle;

    public static void init(Application application){
        if (null==instance){
            synchronized (SkinManager.class){
                if (null==instance){
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance() {
        return instance;
    }

    public SkinManager(Application application){
        mContext = application;
        SkinPreference.init(application);//缓存
        //资源管理类 用于从 app/皮肤 中加载资源
        SkinResources.init(application);
        //注册Activity生命周期,并设置被观察者
        skinActivityLifecycle = new ApplicationActivityLifecycle(this);
        //绑定生命周期
        application.registerActivityLifecycleCallbacks(skinActivityLifecycle);
        //加载上次使用保存的皮肤
        loadSkin(SkinPreference.getInstance().getSkin());

    }

    public void loadSkin(String skinUrl){
        if (TextUtils.isEmpty(skinUrl)){
            //还原默认皮肤
            SkinPreference.getInstance().reset();
            SkinResources.getInstance().reset();
        }else {
            //获取宿主app的Resources
            Resources appResources = mContext.getResources();

            ////反射创建AssetManager 与 Resource
            try {
                AssetManager assetManager = AssetManager.class.newInstance();
                Method method = assetManager.getClass().getDeclaredMethod("addAssetPath",String.class);
                method.invoke(assetManager,skinUrl);

                //根据当前的设备显示器信息 与 配置(横竖屏、语言等) 创建Resources
                Resources skinResource = new Resources(assetManager,
                        appResources.getDisplayMetrics(),appResources.getConfiguration());

                //根据PackageManager获取外部Apk(皮肤包) 包名 获取包信息
                PackageManager packageManager = mContext.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageArchiveInfo(skinUrl,PackageManager.GET_ACTIVITIES);
                String packageName = packageInfo.packageName;
                SkinResources.getInstance().applySkin(skinResource,packageName);
                SkinPreference.getInstance().setSkin(skinUrl);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //通知采集的View 更新皮肤
        //被观察者改变 通知所有观察者
        setChanged();
        notifyObservers(null);
    }


}