package com.together.nosheng.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.databinding.DialogAddNonmemberBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddNonmemberDial extends DialogFragment {

    private ProjectViewModel projectViewModel;
    private DialogAddNonmemberBinding dialogAddNonmemberBinding;

    private String TAG = "Add Nonmember Dialog";
    private Project project;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dialogAddNonmemberBinding = DialogAddNonmemberBinding.inflate(getLayoutInflater());
        View view = dialogAddNonmemberBinding.getRoot();

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        Log.i(TAG, "뷰모델 접근 완료");

        String projectId = getActivity().getIntent().getStringExtra("projectId");
        Log.i(TAG, projectId);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                project = stringProjectMap.get(projectId);
            }
        });

        dialogAddNonmemberBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> nonmember = project.getMembers();
                String nickName = dialogAddNonmemberBinding.etxtNonmemberNickname.getText().toString();
                Map<String, List<String>> newUserTags = project.getUserTags();

                nonmember.add(nickName);
                project.setMembers(nonmember);
                newUserTags.put(nickName,new ArrayList<>());
                projectViewModel.addMember(projectId, nonmember);
                projectViewModel.addUserTags(projectId, newUserTags);

                dismiss();
            }
        });

        dialogAddNonmemberBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
