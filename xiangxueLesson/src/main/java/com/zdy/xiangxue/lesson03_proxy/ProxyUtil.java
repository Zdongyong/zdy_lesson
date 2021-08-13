package com.zdy.xiangxue.lesson03_proxy;

import android.view.View;
import android.widget.Button;

import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson03_proxy.agent.Agent;
import com.zdy.xiangxue.lesson03_proxy.proxyInterface.Message;
import com.zdy.xiangxue.lesson03_proxy.proxyInterface.Wash;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 创建日期：2020-04-29 on 14:12
 * 描述：
 * 作者：zhudongyong
 */
public class ProxyUtil {

    public static void main(String[] args) {
        //静态代理，通过实现接口方式
        Message message = new Lily();
        Agent agent = new Agent(message);
        agent.message();


        // 动态代理
        // 实际上， Proxy.newProxyInstance 会创建一个Class，
        // 与静态代理不同，这个Class不是由具体的.java源文件编译 而来，即没有真正的文件，
        // 只是在内存中按照Class格式生成了一个Class。
        final Lucy lucy = new Lucy();
        Object o = Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),new Class[]{Message.class,Wash.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //此处操作proxy中的任意方法会报错，因为存在
                return method.invoke(lucy,args);
            }
        });
        Message m = (Message) o;
        m.message();
        Wash wash = (Wash) o;
        wash.wash();
    }

}
