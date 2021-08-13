package com.zdy.xiangxue.lesson03_proxy.agent;

import com.zdy.xiangxue.lesson03_proxy.proxyInterface.Message;

/**
 * 创建日期：2020-04-29 on 11:30
 * 描述：代理类
 * 作者：zhudongyong
 */
public class Agent implements Message {
    private Message message;

   public Agent(Message message){
        this.message = message;
   }

    @Override
    public void message() {
        message.message();
    }
}
