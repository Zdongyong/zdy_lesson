package com.zdy.xiangxue.lesson01_genetic;

/**
 * 创建日期：2020-04-26 on 00:10
 * 描述：
 * 作者：zhudongyong
 *
 * //泛型类  T等同于Object
 */
public class Boxx<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }



}
