package com.together.nosheng.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.together.nosheng.R;
import com.together.nosheng.adapter.PostAdapter;
import com.together.nosheng.databinding.FragmentBoardListBinding;
import com.together.nosheng.databinding.LayoutNoteListItemBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.List;
import java.util.Map;

public class BoardListFragment extends Fragment implements View.OnClickListener {

    private FragmentBoardListBinding boardListBinding;
    private ProjectViewModel projectViewModel;
    private String TAG = "BoardListFragment";

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
                boardListBinding.lvPost.setAdapter(new PostAdapter(stringProjectMap.get(projectId).getPosts(), getContext(), projectId, getActivity()));
                Log.i(TAG, stringProjectMap.get(projectId).toString());
            }
        });



        boardListBinding.lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new NewPostFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new NewPostFragment()).commit();
                Log.i("dexxgfsw", "durlRkslsdkjf?");
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
