package com.zdy.skinlibrary;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdy.skinlibrary.entity.SkinPair;
import com.zdy.skinlibrary.utils.SkinResources;
import com.zdy.skinlibrary.utils.SkinThemeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.ViewCompat;

/**
 * 创建日期：2020/7/8 on 23:25
 *
 * 第二步
 *
 * 描述：记录当前xml的需要的属性
 * 作者：zhudongyong
 */
public class SkinAttribute {

    //所有需要修改的view的属性
    private static final List<String> mAttributes = new ArrayList<>();
    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
    }

    //记录换肤需要操作的View与属性信息
    private List<SkinView> mSkinViews = new ArrayList<SkinView>();

    /**
     * 当前xml里面的所有view
     * 一个view对应多个属性   View--List<SkinPair>
     */
    class SkinView {
        //需要修改的view
        View view;
        //需要修改的view所有的属性的集合
        List<SkinPair> skinPairs;

        public SkinView(View view,List<SkinPair> skinPairs){
            this.view = view;
            this.skinPairs = skinPairs;
        }

        /**
         * 修改皮肤
         */
        public void applySkin(){
            applySkinSupport();
            //遍历所有的view属性，根据属性名进行修改
            for (SkinPair skinPair:skinPairs){
                Drawable left = null, top = null, right = null, bottom = null;
                switch (skinPair.getAttributeName()) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(skinPair
                                .getResId());
                        //背景可能是 @color 也可能是 @drawable
                        if (background instanceof Integer) {
                            view.setBackgroundColor((int) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;
                    case "src":
                        background = SkinResources.getInstance().getBackground(skinPair
                                .getResId());
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                    background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;
                    case "textColor":
                        ((TextView) view).setTextColor(SkinResources.getInstance().getColorStateList
                                (skinPair.getResId()));
                        break;
                    case "drawableLeft":
                        left = SkinResources.getInstance().getDrawable(skinPair.getResId());
                        break;
                    case "drawableTop":
                        top = SkinResources.getInstance().getDrawable(skinPair.getResId());
                        break;
                    case "drawableRight":
                        right = SkinResources.getInstance().getDrawable(skinPair.getResId());
                        break;
                    case "drawableBottom":
                        bottom = SkinResources.getInstance().getDrawable(skinPair.getResId());
                        break;
                    default:
                        break;
                }
                if (null != left || null != right || null != top || null != bottom) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right,
                            bottom);
                }
            }

        }

        private void applySkinSupport() {
            if (view instanceof SkinViewSurpport) {
                ((SkinViewSurpport) view).applySkin();
            }
        }
    }

    /**
     * 记录下一个VIEW身上哪几个属性需要换肤textColor/src
     */
    public void look(View view ,AttributeSet attrs){
        List<SkinPair> mSkinPars = new ArrayList<>();
        for (int i= 0;i<attrs.getAttributeCount();i++){
            //获得属性名  textColor/background
            String attributeName = attrs.getAttributeName(i);
//            Log.i("123123", "look: "+attributeName);
            if (mAttributes.contains(attributeName)){

                String attributeValue = attrs.getAttributeValue(i);

                //xml属性当中存在
                //情况一 带?的：修改 系统的
                //情况二 带@的：修改
                //情况三 带#的：不修改 强制性
                if (attributeValue.startsWith("#")){
                    continue;
                }
                int resId;
                if (attributeValue.startsWith("?")){//系统的
                    int attrId = Integer.valueOf(attributeValue.substring(1));//从第一位后面开始截取全部
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                }else {
                    resId = Integer.valueOf(attributeValue.substring(1));//从第一位后面开始截取全部
                }
                SkinPair skinPair = new SkinPair(attributeName,resId);
                mSkinPars.add(skinPair);//记录View的属性
            }
        }


        if (!mSkinPars.isEmpty() && view instanceof SkinViewSurpport){
            SkinView skinView = new SkinView(view,mSkinPars);
            skinView.applySkin();//启动就判断是否存在需要修改的skin
            mSkinViews.add(skinView);
        }

    }

    /**
     * 引用皮肤
     */
    public void applySkin(){
        for (SkinView view:mSkinViews){
            view.applySkin();
        }
    }

}