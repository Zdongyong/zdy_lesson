package com.zdy.xiangxue.lesson_rxJava.api;

import com.zdy.xiangxue.lesson_rxJava.bean.ProjectList;
import com.zdy.xiangxue.lesson_rxJava.bean.ProjectTree;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 创建日期：2020/6/8 on 20:05
 * 描述：
 * 作者：zhudongyong
 */

public interface WanAndroidApi {


    //查询项目分类
    @GET("project/tree/json")
    Observable<ProjectTree> getProjectTree();

    //查询项目列表
    @GET("project/list/{pageIndex}/json")
    Observable<ProjectList> getProjectList(@Path ("pageIndex") int pageIndex, @Query("cid") int cid);


}
