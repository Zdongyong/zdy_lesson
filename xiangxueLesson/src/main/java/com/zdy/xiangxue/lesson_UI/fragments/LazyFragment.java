package com.zdy.xiangxue.lesson_UI.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * 创建日期：2020/7/29 on 23:58
 * 描述：
 * 作者：zhudongyong
 */
public abstract class LazyFragment extends Fragment {

    private static final String TAG = "LazyFragment";

    private View rootView = null;
    private boolean isCreated = false;//是否创建
    private boolean isUPVisible = false;//记录上一次是否可见 否则会多次stop

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null==rootView){
            rootView = inflater.inflate(getLayoutRes(),container, false);
        }
        isCreated = true;
        intView(rootView);

        // 解决第一次一直初始化loading一直显示的问题 【第二版2.1】
        if (getUserVisibleHint()) {
            // 手动来分发下
            setUserVisibleHint(true);
        }

        return rootView;
    }

    //加载布局
    protected abstract int getLayoutRes();

    //初始化布局
    protected abstract void intView(View view);

    //加载数据
    protected abstract void onFragmentLoad();

    protected abstract void onFragmentLoadStop();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isCreated){
            // 记录上一次可见的状态: && isVisibleStateUP

            if (isVisibleToUser && !isUPVisible) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && isUPVisible){
                dispatchUserVisibleHint(false);
            }
        }

    }

    // TODO 分发 可见 不可见 的动作
    private void dispatchUserVisibleHint(boolean visibleState) {

        this.isUPVisible = visibleState;

        // TODO 为了解决第一个问题
        if (visibleState && isParentInvisible()) {
            return;
        }

        if (visibleState) {
            // 加载数据
            onFragmentLoad();  // 都是对第一层有效，嵌套无效

            // TODO 为了解决第二个问题，T1 到 T2 T2里面嵌套的ViewPager的Fragment默认不会分发执行
            // 手动 嵌套 分发执行
            // TODO 为了解决第二个问题，T1 到 T2 T2里面嵌套的ViewPager的Fragment默认不会分发执行
            // 在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
            // 此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
            // 需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
            dispatchChildVisibleState(true);

        } else {
            // 停止一切操作
            onFragmentLoadStop();  // 都是对第一层有效，嵌套无效

            // TODO 为了解决第二个问题，T1 到 T2 T2里面嵌套的ViewPager的Fragment默认不会分发执行
            dispatchChildVisibleState(false);
        }
    }

    // TODO 为了解决第二个问题，T1 到 T2 T2里面嵌套的ViewPager的Fragment默认不会分发执行
    //  解决：需要手动的分发执行嵌套Fragment里面的
    protected void dispatchChildVisibleState(boolean state) {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment: fragments) { // 循环遍历 嵌套里面的 子 Fragment 来分发事件操作
                if (fragment instanceof LazyFragment &&
                        !fragment.isHidden() &&
                        fragment.getUserVisibleHint()) {
                    ((LazyFragment)fragment).dispatchUserVisibleHint(state);
                }
            }
        }
    }

    // TODO 判断 父控件 是否可见， 什么意思？ 例如：  Fragment2_vp1子Fragment  的  父亲/父控件==Fragment2
    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof LazyFragment) {
            LazyFragment fragment = (LazyFragment) parentFragment;
            return !fragment.isUPVisible;
        }
        return false;
    }

    //解决在子fragment中跳转到activity时，没有执行setUserVisibleHint
    //setUserVisibleHint不是生命周期的方法
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && !isUPVisible) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint() && isUPVisible) {
            dispatchChildVisibleState(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}