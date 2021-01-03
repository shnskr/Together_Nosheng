package com.together.nosheng.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.together.nosheng.adapter.CheckListAdapter;
import com.together.nosheng.databinding.FragmentBoardChecklistBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.Map;

public class ChecklistBoardFragment extends Fragment {

    private FragmentBoardChecklistBinding binding;
    private ProjectViewModel projectViewModel;

    private Project currentProject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentBoardChecklistBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String projectId = requireActivity().getIntent().getStringExtra("projectId");

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                currentProject = stringProjectMap.get(projectId);
                Log.i("daldal", currentProject.getCheckLists().get(GlobalApplication.firebaseUser.getUid()).toString());
                CheckListAdapter adapter = new CheckListAdapter(currentProject.getCheckLists().get(GlobalApplication.firebaseUser.getUid()));
                binding.rvCheck.setAdapter(adapter);
                binding.rvCheck.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Boolean> checkLists = currentProject.getCheckLists().get(GlobalApplication.firebaseUser.getUid());
                if (checkLists.containsKey(" ")) {
                    Toast.makeText(requireContext(), "빈칸이 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    checkLists.put(" ", false);
                    projectViewModel.updateCheckList(projectId, checkLists);
                }
            }
        });

        return view;
    }
}