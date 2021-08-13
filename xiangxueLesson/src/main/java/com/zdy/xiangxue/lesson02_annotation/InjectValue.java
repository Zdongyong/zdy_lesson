package com.zdy.xiangxue.lesson02_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建日期：2020-04-28 on 09:55
 * 描述：
 * 作者：zhudongyong
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectValue {

    String name() default "1";

}
