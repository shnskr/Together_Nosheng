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
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.together.nosheng.adapter.CheckListAdapter;
import com.together.nosheng.databinding.FragmentBoardChecklistBinding;
import com.together.nosheng.model.project.CheckList;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.List;
import java.util.Map;

public class ChecklistBoardFragment extends Fragment {

    private FragmentBoardChecklistBinding binding;
    private ProjectViewModel projectViewModel;

    private Project currentProject;

    CheckListAdapter adapter;

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
                adapter = new CheckListAdapter(currentProject.getCheckLists().get(GlobalApplication.firebaseUser.getUid()));
                binding.rvCheck.setAdapter(adapter);
                binding.rvCheck.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });

        getParentFragmentManager().setFragmentResultListener("bundle", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (result.getBoolean("click")) {
                    CheckList checkLists = currentProject.getCheckLists().get(GlobalApplication.firebaseUser.getUid());
                    if (checkLists.getItem().contains("")) {
                        Toast.makeText(requireContext(), "빈칸이 존재합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        checkLists.getItem().add("");
                        checkLists.getCheck().add(false);
                        projectViewModel.updateCheckList(projectId, checkLists);
                    }
                }
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rvCheck.clearFocus();
                List<String> item = adapter.getItem();
                List<Boolean> check = adapter.getCheck();

                CheckList checkList = new CheckList();

                checkList.setItem(item);
                checkList.setCheck(check);

                projectViewModel.updateCheckList(projectId, checkList);

                Toast.makeText(requireContext(), "저장 완료", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}