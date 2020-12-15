package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.adapter.BoardAdapter;
import com.together.nosheng.adapter.HomeAdapter;
import com.together.nosheng.databinding.ActivityFragmentBoardBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BoardFragmentActivity extends Fragment implements View.OnClickListener{

    private ActivityFragmentBoardBinding boardBinding;

    private ProjectViewModel projectViewModel;

    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;

    private String TAG = "BoardFragmentActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardBinding = ActivityFragmentBoardBinding.inflate(inflater, container, false);
        View view = boardBinding.getRoot();

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new BoardListFragment()).commit();

        boardBinding.fabMain.setOnClickListener(this);
        boardBinding.fabSub1.setOnClickListener(this);
        boardBinding.fabSub2.setOnClickListener(this);

//        for (Fragment fragment: getActivity().getSupportFragmentManager().getFragments()) {
//            if (fragment.isVisible()) {
//                if(fragment instanceof NewPostFragment){
//                    boardBinding.fabMain.setVisibility(View.GONE);
//                } else {
//                    boardBinding.fabMain.setVisibility(View.VISIBLE);
//                }
//            }
//        }

        return view;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = getParentFragmentManager().findFragmentById(R.id.board_container);
        switch (v.getId()) {
            case R.id.fab_main:
                toggleFab();
                break;

            case R.id.fab_sub1:
                if(fragment instanceof BoardListFragment){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new ChecklistBoardFragment()).commit();
                    boardBinding.fabSub1.setImageResource(R.drawable.ic_note);
                    boardBinding.fabSub2.setImageResource(R.drawable.ic_add_check);
                }else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new BoardListFragment()).commit();
                    boardBinding.fabSub1.setImageResource(R.drawable.ic_check_circle);
                    boardBinding.fabSub2.setImageResource(R.drawable.ic_note_add);
                }
                toggleFab();
                break;

            case R.id.fab_sub2:
                if(fragment instanceof BoardListFragment){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new NewPostFragment()).commit();
                }else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new ChecklistBoardFragment()).commit();
                }
                toggleFab();
                break;
            }
    }
    private void toggleFab(){

        if (isFabOpen) {
            boardBinding.fabMain.setImageResource(R.drawable.ic_more);
            boardBinding.fabSub1.startAnimation(fab_close);
            boardBinding.fabSub2.startAnimation(fab_close);
            boardBinding.fabSub1.setClickable(false);
            boardBinding.fabSub2.setClickable(false);
            isFabOpen = false;
        } else {
            boardBinding.fabMain.setImageResource(R.drawable.ic_close);
            boardBinding.fabSub1.startAnimation(fab_open);
            boardBinding.fabSub2.startAnimation(fab_open);
            boardBinding.fabSub1.setClickable(true);
            boardBinding.fabSub2.setClickable(true);
            isFabOpen = true;
        }
    }
}