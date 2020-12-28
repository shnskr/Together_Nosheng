package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.together.nosheng.adapter.HomeAdapter;
import com.together.nosheng.databinding.ActivityFragmentHomeBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragmentActivity extends Fragment {

    private ActivityFragmentHomeBinding homeBinding;

    private ProjectViewModel projectViewModel;
    private List<String> projectIds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityFragmentHomeBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();


        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        projectViewModel.setUserProjects();

        projectViewModel.getUserProjects().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> userProject) {
                homeBinding.lvProject.setAdapter(new HomeAdapter(userProject, getActivity(), requireActivity(), projectIds));
            }
        });


        homeBinding.btnNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewTripActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
