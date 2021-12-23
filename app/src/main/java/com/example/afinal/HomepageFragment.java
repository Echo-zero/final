package com.example.afinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomepageFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, null);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //显示用户的积分
        TextView pointHave = (TextView) getActivity().findViewById(R.id.pointHave);
        TextView pointGet = (TextView) getActivity().findViewById(R.id.pointGet);
        TextView pointUsed = (TextView) getActivity().findViewById(R.id.pointUsed);
        //按钮跳转其他事件
        ImageButton message = (ImageButton) getActivity().findViewById(R.id.imageView);//垃圾分类小知识
        ImageButton compete = (ImageButton) getActivity().findViewById(R.id.compete);//答题入口1
        ImageButton homepage_search = (ImageButton) getActivity().findViewById(R.id.search);//照片查询
        ImageButton shop = (ImageButton) getActivity().findViewById(R.id.shop);//商城
        ImageButton quiz = (ImageButton) getActivity().findViewById(R.id.quizButton);//答题入口2

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
