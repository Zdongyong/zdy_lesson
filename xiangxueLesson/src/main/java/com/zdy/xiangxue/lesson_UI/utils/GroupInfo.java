package com.zdy.xiangxue.lesson_UI.utils;

/**
 * 创建日期：2020/7/22 on 23:59
 * 描述：
 * 作者：zhudongyong
 */
public class GroupInfo {

    private String name;
    private String value;

    public GroupInfo(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}