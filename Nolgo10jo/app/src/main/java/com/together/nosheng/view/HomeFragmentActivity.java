package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.together.nosheng.databinding.ActivityFragmentHomeBinding;


public class HomeFragmentActivity extends Fragment {

    private ActivityFragmentHomeBinding homeBinding;
    //private HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityFragmentHomeBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();

        homeBinding.btnNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BottomBtnActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
