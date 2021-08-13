package com.zdy.skinlibrary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 创建日期：2020/7/8 on 23:33
 * 描述：记录当前使用的skin路径
 * 作者：zhudongyong
 */
public class SkinPreference {

    private static final String SKIN_SHARED = "skin_shared";
    private static final String KEY_SKIN_PATH = "skin_path";

    private volatile static SkinPreference instance;
    private final SharedPreferences mPref;


    private SkinPreference(Context context) {
        mPref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if (null==instance){
            synchronized (SkinPreference.class){
                if (null==instance){
                    instance = new SkinPreference(context);
                }
            }
        }
    }


    public static SkinPreference getInstance() {
        return instance;
    }

    public void setSkin(String skinPath) {
        mPref.edit().putString(KEY_SKIN_PATH, skinPath).apply();
    }

    public void reset() {
        mPref.edit().remove(KEY_SKIN_PATH).apply();
    }

    public String getSkin() {
        return mPref.getString(KEY_SKIN_PATH, null);
    }


}