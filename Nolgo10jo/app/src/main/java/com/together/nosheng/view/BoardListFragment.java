package com.together.nosheng.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.together.nosheng.R;
import com.together.nosheng.adapter.BoardAdapter;
import com.together.nosheng.databinding.FragmentBoardListBinding;
import com.together.nosheng.databinding.FragmentNewPostBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BoardListFragment extends Fragment {

    private FragmentBoardListBinding boardListBinding;
    private ProjectViewModel projectViewModel;
    private String TAG = "BoardListFragment";

    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardListBinding = FragmentBoardListBinding.inflate(inflater, container, false);
        View view = boardListBinding.getRoot();

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        Log.i(TAG, "접근완료");

        String projectId = getActivity().getIntent().getStringExtra("projectId");
        Log.i(TAG, projectId);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                boardListBinding.lvPost.setAdapter(new BoardAdapter(stringProjectMap.get(projectId).getPosts()));
                Log.i(TAG, stringProjectMap.get(projectId).toString());
            }
        });

        boardListBinding.lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new NewPostFragment()).commit();

            }
        });

        return view;
    }

}
