package com.zdy.skinlibrary.entity;

/**
 * 创建日期：2020/7/8 on 23:24
 * 描述：当前View里面的属性
 * 记录一个属性  属性名字--对应的资源id
 * 作者：zhudongyong
 */
public class SkinPair {

    //属性名
    String attributeName;
    //对应的资源id
    int resId;

    public SkinPair(String attributeName,int resId){
        this.attributeName = attributeName;
        this.resId = resId;
    }


    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}