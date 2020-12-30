package com.together.nosheng.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.adapter.MemberAdapter;
import com.together.nosheng.databinding.ActivityFragmentMemberBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;

public class MemberFragmentActivity extends Fragment{

    private ActivityFragmentMemberBinding memberBinding;
    private ProjectViewModel projectViewModel;
    private UserViewModel userViewModel;

    private String TAG = "MemberFragmentActivity";

    private Project project;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberBinding = ActivityFragmentMemberBinding.inflate(inflater, container, false);
        View view = memberBinding.getRoot();

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        Log.i(TAG, "뷰모델 접근 완료");
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        String projectId = getActivity().getIntent().getStringExtra("projectId");
        Log.i(TAG, projectId);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                project = stringProjectMap.get(projectId);
                List<String> members = project.getMembers();
                Map<String,List<String>> userTags = project.getUserTags();

                Log.i(TAG+"??", projectId);
                Log.i(TAG+"퍄??", members.toString());

                userViewModel.setUserNickName(members);
                userViewModel.getUserNickNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        Log.i(TAG+"??strings", strings.toString());
                        memberBinding.lvMembers.setAdapter(new MemberAdapter(getContext(), userViewModel, projectViewModel, projectId, members, userTags, strings));
                    }
                });
            }
        });




        Bundle args = new Bundle();
        memberBinding.lvMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uid = project.getMembers().get(position);
                args.putStringArrayList("utags", (ArrayList<String>) project.getUserTags().get(uid));
                Log.i("가져오나 봅시다!",  project.getUserTags().get(uid).toString());
                args.putString("Uid", uid);

                DialogFragment dialogFragment = new TagDial();
                dialogFragment.setArguments(args);
                dialogFragment.show(getChildFragmentManager(),"tagDial");
            }
        });

        memberBinding.btnAddNonmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddNonmemberDial();
                dialogFragment.show(getChildFragmentManager(),"add nonmember dialog");
            }
        });
        return view;
    }
}