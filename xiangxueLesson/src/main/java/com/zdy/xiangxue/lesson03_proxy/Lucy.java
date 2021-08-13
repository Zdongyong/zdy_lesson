package com.zdy.xiangxue.lesson03_proxy;

import com.zdy.xiangxue.lesson03_proxy.proxyInterface.Message;
import com.zdy.xiangxue.lesson03_proxy.proxyInterface.Wash;

/**
 * 创建日期：2020-04-29 on 10:52
 * 描述：真实类
 * 作者：zhudongyong
 */
public class Lucy implements Message, Wash {

    @Override
    public void message() {
        System.out.println("=====我也会按摩========");
    }

    @Override
    public void wash() {
        System.out.println("======我还会足浴======");
    }
}
