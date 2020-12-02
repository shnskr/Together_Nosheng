package com.together.nosheng.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.together.nosheng.databinding.ActivityFragmentPlanBinding;


public class PlanFragmentActivity extends Fragment {

    private ActivityFragmentPlanBinding planBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planBinding = ActivityFragmentPlanBinding.inflate(inflater, container, false);
        View view = planBinding.getRoot();

        return view;
    }
}