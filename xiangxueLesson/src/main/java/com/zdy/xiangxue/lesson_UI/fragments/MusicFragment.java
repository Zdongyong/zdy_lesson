package com.zdy.xiangxue.lesson_UI.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_UI.adapters.RecyclerFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：2020/7/8 on 22:14
 * 描述：
 * 作者：zhudongyong
 */
public class MusicFragment extends LazyFragment {

    private static final String TAG = "Fragment";

    private RecyclerView recyclerView;

    public static MusicFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        Log.i(TAG, "newInstance: MusicFragment");
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_music;
    }

    @Override
    protected void intView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        final RecyclerFragmentAdapter adapter = new RecyclerFragmentAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int THRESHOLD_LOAD_MORE = 3;
            private boolean hasLoadMore;

            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hasLoadMore = false;
                }

                if (newState != RecyclerView.SCROLL_STATE_DRAGGING && !hasLoadMore) {
                    int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int offset = recyclerView.getAdapter().getItemCount() - lastPosition - 1;
                    if (offset <= THRESHOLD_LOAD_MORE) {
                        hasLoadMore = true;
                        adapter.mData.addAll(getData());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private int i = 0;

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for(int tempI = i; i < tempI + 10; i ++) {
            data.add("ChildView item " + i);
        }
        return data;
    }

    @Override
    protected void onFragmentLoad() {
        Log.i(TAG, "intData: music");
    }

    @Override
    protected void onFragmentLoadStop() {
        Log.i(TAG, "stopData: music");
    }
}