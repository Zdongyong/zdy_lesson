package com.zdy.xiangxue.lesson03_proxy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson03_proxy.proxyInterface.Event;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 创建日期：2020-04-29 on 10:27
 * 描述：利用反射、注解、动态代理实现OnClick事件的自动注入
 *
 * @Click({R.id.bottom,R.id.end})
 * public void abc(View view){ }
 * 作者：zhudongyong
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_proxy);
        InjectEventUtil.injectEvent(this);
        Button actionProxy = (Button) findViewById(R.id.bt_action_proxy);
//        actionProxy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("=====谁点了我===");
//            }
//        });
//        actionProxy.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                System.out.println("=====谁按了我===");
//                return false;
//            }
//        });
    }

    @OnClick(id = {R.id.bt_action_proxy, R.id.bt_static_proxy})
    public void onClick(View view){
        System.out.println("=====谁点了我===onClick");
    }

    @OnLongClick(id = {R.id.bt_action_proxy, R.id.bt_static_proxy})
    public boolean onLongClick(View view){
        System.out.println("=====谁按了我===onLongClick");
        return true;
    }
}
