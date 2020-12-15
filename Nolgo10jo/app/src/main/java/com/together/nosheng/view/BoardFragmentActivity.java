package com.together.nosheng.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentBoardBinding;

public class BoardFragmentActivity extends Fragment {

    private ActivityFragmentBoardBinding boardBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardBinding = ActivityFragmentBoardBinding.inflate(inflater, container, false);
        View view = boardBinding.getRoot();

        return view;
    }
}