package com.zdy.xiangxue.lesson02_annotation;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 创建日期：2020-04-28 on 10:00
 * 描述：
 * 作者：zhudongyong
 */
public class InjectValueUtil {

    public static void injectView(Activity activity){
        //通过反射获取实现类
        Class<? extends Activity> clazz = activity.getClass();
        //获取改类中的属性
        //getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
        //getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        Bundle bundle = activity.getIntent().getExtras();
        if (null==bundle){
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            //获取key
            String key = (null==field.getName())?"":field.getName();
            //判断是否被注解InjectValue声明
            if (field.isAnnotationPresent(InjectValue.class)){
                if (bundle.containsKey(key)){
//                    InjectValue injectValue = field.getAnnotation(InjectValue.class);//获取注解
//                    String name = injectValue.name();
                    String name = bundle.getString(key);
                    field.setAccessible(true);//受保护的是否可以修改
                    try {
                        field.set(activity,name);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


}
