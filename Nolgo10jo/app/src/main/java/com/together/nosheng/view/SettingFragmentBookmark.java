package com.together.nosheng.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.together.nosheng.R;
import com.together.nosheng.adapter.BookmarkAdapter;
import com.together.nosheng.databinding.SettingFragmentBookmarkBinding;
import com.together.nosheng.model.plan.Plan;

import com.together.nosheng.viewmodel.PlanViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingFragmentBookmark extends Fragment {
    private SettingFragmentBookmarkBinding binding;
    private PlanViewModel planViewModel;

    public static SettingFragmentBookmark newInstance() {
        return new SettingFragmentBookmark();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SettingFragmentBookmarkBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        planViewModel = new ViewModelProvider(requireActivity()).get(PlanViewModel.class);
        planViewModel.setUserBookmark();

        binding.rvBookmark.setLayoutManager(new LinearLayoutManager(requireContext()));

        planViewModel.getUserBookmark().observe(getViewLifecycleOwner(), new Observer<Map<String, Plan>>() {
            @Override
            public void onChanged(Map<String, Plan> stringPlanMap) {
                if (stringPlanMap.size() == 0) {
                    binding.tvBookmarkMessage.setVisibility(View.VISIBLE);
                } else {
                    binding.tvBookmarkMessage.setVisibility(View.INVISIBLE);
                }
                binding.rvBookmark.setAdapter(new BookmarkAdapter(stringPlanMap, requireContext()));
            }
        });

        return view;
    }

}