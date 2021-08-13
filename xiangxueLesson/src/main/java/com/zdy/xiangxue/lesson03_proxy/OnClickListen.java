package com.zdy.xiangxue.lesson03_proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建日期：2020-04-29 on 19:23
 * 描述：
 * 作者：zhudongyong
 */


@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClickListen {

    Class listenType();//监听类型

    String listenSet();//设置监听
}
