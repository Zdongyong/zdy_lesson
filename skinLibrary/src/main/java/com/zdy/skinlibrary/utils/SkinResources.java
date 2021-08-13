package com.zdy.skinlibrary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * 创建日期：2020/7/10 on 17:09
 * 描述：皮肤资源解析
 * 作者：zhudongyong
 */
public class SkinResources {

    private String mSkinPkgName;
    private boolean isDefaultSkin = true;

    private Resources mAppResources;//app原始的Resources
    private Resources mSkinResources;//皮肤包资源包的Resources


    private SkinResources(Context context){
        mAppResources = context.getResources();
    }


    //单例
    private volatile static SkinResources instance;
    public static void init(Context context) {
        if (null==instance){
            synchronized(SkinResources.class){
                if (null==instance){
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    /**
     * 重置皮肤
     */
    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    /**
     * 使用皮肤
     * @param resources
     * @param pkgName
     */
    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }


    public Object getBackground(int resId){
        String resourceName =  mAppResources.getResourceEntryName(resId);
        if ("color".equalsIgnoreCase(resourceName)){
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }
    }

    /**
     * 批量获取app中的view的资源文件属性
     *
     *然后根据对应的view属性的key，去目标皮肤库中查找资源
     *
     * @param resId
     * @return
     */
    private int getIdentifier(int resId){
        if (isDefaultSkin){
            return  resId;
        }
        String name = mAppResources.getResourceEntryName(resId);
        String defType = mAppResources.getResourceTypeName(resId);
        int skinId = mSkinResources.getIdentifier(name,defType,mSkinPkgName);
        return skinId;
    }


    /**
     * 获取颜色值
     * @param resId
     * @return
     */
    public int getColor(int resId){
        if (isDefaultSkin){
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId==0){
            return mAppResources.getColor(skinId);
        }
        return mSkinResources.getColor(skinId);
    }

    /**
     * textColor
     * @param resId
     * @return
     */
    public ColorStateList getColorStateList(int resId){
        if (isDefaultSkin){
            return mAppResources.getColorStateList(resId);
        }

        int skinId = getIdentifier(resId);
        if (skinId==0){
            return mAppResources.getColorStateList(skinId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawable(int resId){
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        //通过 app的resource 获取id 对应的 资源名 与 资源类型
        //找到 皮肤包 匹配 的 资源名资源类型 的 皮肤包的 资源 ID
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }



}