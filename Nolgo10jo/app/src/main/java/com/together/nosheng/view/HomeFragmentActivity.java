package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentHomeBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.viewmodel.HomeAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragmentActivity extends Fragment {

    private ActivityFragmentHomeBinding homeBinding;
    private HomeAdapter homeAdapter;
    FloatingActionButton btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityFragmentHomeBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();

        Log.i("aptpsldkmfwlekf", "sdfsdfs");
        homeBinding.btnNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ㅁㄴㅇㅁ", "ㅂㅂㅂ");
                Intent intent = new Intent(getActivity(), BottomBtnActivity.class);
                startActivity(intent);
            }
        });
        Log.i("aptpsldkmfwlekf", "sdfsd654132fs");
        return view;
    }
}
