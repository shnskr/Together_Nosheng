package com.together.nosheng.view;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.adapter.MemberAdapter;
import com.together.nosheng.adapter.TagDialAdapter;
import com.together.nosheng.databinding.DialogTagBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TagDial extends DialogFragment {

    private ProjectViewModel projectViewModel;
    private DialogTagBinding dialogTagBinding;

    private String TAG = "Tag Dialog";
    private Project project;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogTagBinding = DialogTagBinding.inflate(getLayoutInflater());
        View view = dialogTagBinding.getRoot();

        Bundle margs = getArguments();
        String uid = margs.getString("Uid");
        List<String> utags = (List<String>) margs.getStringArrayList("utags");
        Log.i("가져오나 봅시다?",  utags.toString());

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        Log.i(TAG, "접근완료");

        String projectId = getActivity().getIntent().getStringExtra("projectId");
        Log.i(TAG, projectId);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                project = stringProjectMap.get(projectId);
                dialogTagBinding.lvTag.setAdapter(new TagDialAdapter(project.getTags(), projectId, requireActivity(), project.getUserTags().get(uid)));
                Log.i(TAG, project.toString());

                List<String> members = project.getMembers();
                List<String> tags = project.getTags();
                for(String member : members) {
                    Map<String, List<String>> userTags = project.getUserTags();
                    List<String> utags = userTags.get(member);
                    for(String utag : utags){
                        if(!tags.contains(utag)){
                            utags.remove(utag);
                            userTags.put(member,utags);
                            projectViewModel.addUserTags(projectId,userTags);
                        }
                    }
                }
            }
        });



        dialogTagBinding.btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagName = dialogTagBinding.etxtTagname.getText().toString();
                tagName.trim();
                if(dialogTagBinding.etxtTagname != null || tagName != ""){
                    List<String> tags = project.getTags();
                    if(!tags.contains(tagName)){
                        tags.add(tagName);
                        projectViewModel.addTag(projectId, tags);
                        dialogTagBinding.etxtTagname.setText(null);
                        Log.i(TAG, tags.toString());
                        Log.i(TAG, "tag name add!");
                    } else{
                        Toast.makeText(getContext(), "이미 존재합니다.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "tag name add denied!");
                    }
                } else {
                    Toast.makeText(getContext(), "tag를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "tag name failed!");
                }
            }
        });

        dialogTagBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "uid로 리스트 가져오기"+project.getUserTags().get(uid));
                project.getUserTags().put(uid,project.getUserTags().get(uid));
                Log.i(TAG+"dhsk?",project.getUserTags().get(uid).toString());
                projectViewModel.addUserTags(projectId, project.getUserTags());
                Log.i(TAG, "user tags add complete");
                dismiss();
            }
        });

        dialogTagBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
