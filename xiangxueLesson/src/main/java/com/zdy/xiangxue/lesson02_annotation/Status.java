package com.zdy.xiangxue.lesson02_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 创建日期：2020/8/13 on 18:25
 * 描述：
 * 作者：zhudongyong
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Status {

    int UN_KNOW = -1;
    int UN_START = 0;
    int PROGRESSING = 1;
    int COMPLETED = 2;

}
