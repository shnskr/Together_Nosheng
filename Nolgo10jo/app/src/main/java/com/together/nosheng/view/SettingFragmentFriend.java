package com.together.nosheng.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.adapter.SettingAdapter;
import com.together.nosheng.databinding.SettingFragmentBookmarkBinding;
import com.together.nosheng.databinding.SettingFragmentFriendBinding;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingFragmentFriend extends Fragment {
    private SettingFragmentFriendBinding binding;

    private UserViewModel userViewModel;

    public static SettingFragmentFriend newInstance() {
        return new SettingFragmentFriend();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SettingFragmentFriendBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setUserFriendList();

        binding.rvFriend.setLayoutManager(new LinearLayoutManager(requireContext()));

        userViewModel.getUserFriendList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users.size() == 0) {
                    binding.tvFriendMessage.setVisibility(View.VISIBLE);
                } else {
                    binding.tvFriendMessage.setVisibility(View.INVISIBLE);
                }
                binding.rvFriend.setAdapter(new SettingAdapter(users));
            }
        });

        return view;
    }
}
