package com.zdy.xiangxue.lesson02_annotation;

/**
 * 创建日期：2020-04-26 on 15:09
 * 描述：注解
 * 作者：zhudongyong
 */
@Lance("古力")
public class AnnotationUtil {

    @Lance("")
    private String value;

    /**
     * 作用域不被允许
     * @Lance("")
     */
    private void get(){

    };


    public static void main(String[] args) {

    }
}
