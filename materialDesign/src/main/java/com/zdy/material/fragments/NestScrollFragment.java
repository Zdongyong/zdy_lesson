package com.zdy.material.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdy.material.R;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NestScrollFragment extends Fragment {

    private String mText;
//    private RecyclerView mRecyclerView;

    public static NestScrollFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        NestScrollFragment fragment = new NestScrollFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_old_nest,container,false);
//        mRecyclerView = view.findViewById(R.id.recycler_view);
//        mRecyclerView = view.findViewById(R.id.tab);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = getArguments().getString("title","");
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
//        mRecyclerView.setLayoutManager(linearLayoutManager);


//        CommonRecyclerAdapter<String> commonRecyclerAdapter = new CommonRecyclerAdapter<String>(requireContext(),createData(), R.layout.item_recycle) {
//
//            @Override
//            public void convert(CommonRecyclerHolder holder, String item, int position, boolean isScrolling) {
//                holder.setText(R.id.item_tv,item);
//            }
//        };
//        mRecyclerView.setAdapter(commonRecyclerAdapter);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));

    }

    private List<String> createData(){
        ArrayList<String> result = new ArrayList<>(100);
        for(int i = 0; i < 100; i++){
            result.add(mText + i);
        }
        return result;
    }
}