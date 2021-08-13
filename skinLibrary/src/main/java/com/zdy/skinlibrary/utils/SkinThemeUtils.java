package com.zdy.skinlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;

import com.zdy.skinlibrary.R;

/**
 * 创建日期：2020/7/9 on 00:52
 * 描述：系统主题、系统属性的修改方式
 * 作者：zhudongyong
 */
public class SkinThemeUtils {

    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            R.attr.colorPrimaryDark
    };
    private static int[] STATUSBAR_COLOR_ATTRS = {android.R.attr.statusBarColor, android.R.attr
            .navigationBarColor
    };

    /**
     * 获得theme中的属性中定义的 资源id
     * @param context
     * @param attrs
     * @return
     */
    public static int[] getResId(Context context, int[] attrs) {
        int[] resIds = new int[attrs.length];
        TypedArray a = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < attrs.length; i++) {
            resIds[i] = a.getResourceId(i, 0);
        }
        a.recycle();
        return resIds;
    }


    /**
     * 更新状态栏
     * @param activity
     */
    public static void updateStatusBarColor(Activity activity) {
        //5.0以上才能修改
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        //获得 statusBarColor 与 nanavigationBarColor (状态栏颜色)
        //当与 colorPrimaryDark  不同时 以statusBarColor为准
        int[] resIds = getResId(activity, STATUSBAR_COLOR_ATTRS);
        int statusBarColorResId = resIds[0];
        int navigationBarColor = resIds[1];

        //如果直接在style中写入固定颜色值(而不是 @color/XXX ) 获得0
        if (statusBarColorResId != 0) {
            int color = SkinResources.getInstance().getColor(statusBarColorResId);
            activity.getWindow().setStatusBarColor(color);
        } else {
            //获得 colorPrimaryDark
            int colorPrimaryDarkResId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
            if (colorPrimaryDarkResId != 0) {
                int color = SkinResources.getInstance().getColor(colorPrimaryDarkResId);
                activity.getWindow().setStatusBarColor(color);
            }
        }
        if (navigationBarColor != 0) {
            int color = SkinResources.getInstance().getColor
                    (navigationBarColor);
            activity.getWindow().setNavigationBarColor(color);

        }
    }






}