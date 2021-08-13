package com.zdy.xiangxue.lesson_rxJava.retrofit_okhttp;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建日期：2020/6/8 on 20:34
 * 描述：
 * 作者：zhudongyong
 */

public class HttpUtil{

    private static final String BasePath = "https://www.wanandroid.com";


    public static Retrofit getOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.addInterceptor(new StethoInterceptor())
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BasePath)
                .client(okHttpClient)

                // TODO 响应RxJava
                // 添加一个json解析的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                // 添加rxjava处理工具
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}
