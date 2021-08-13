package com.zdy.xiangxue.lesson03_proxy;

import com.zdy.xiangxue.lesson03_proxy.proxyInterface.Message;

/**
 * 创建日期：2020-04-29 on 10:51
 * 描述：真实类
 * 作者：zhudongyong
 */
public class Lily implements Message {
    @Override
    public void message() {
        System.out.println("========我会按摩====");
    }
}
