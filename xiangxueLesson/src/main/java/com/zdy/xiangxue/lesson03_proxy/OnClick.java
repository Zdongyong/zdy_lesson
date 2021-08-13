package com.zdy.xiangxue.lesson03_proxy;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

/**
 * 创建日期：2020-04-29 on 19:23
 * 描述：
 * 作者：zhudongyong
 */


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@OnClickListen(listenType = View.OnClickListener.class,listenSet = "setOnClickListener")
public @interface OnClick {

    @IdRes int[] id();

}
