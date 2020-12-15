package com.together.nosheng.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.together.nosheng.databinding.FragmentBoardChecklistBinding;

public class ChecklistBoardFragment extends Fragment {

    private FragmentBoardChecklistBinding boardChecklistBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardChecklistBinding = FragmentBoardChecklistBinding.inflate(inflater, container, false);
        View view = boardChecklistBinding.getRoot();

        return view;
    }
}