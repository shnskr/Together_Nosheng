package com.together.nosheng.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private RecyclerView mRecyclerView;
    private UserViewModel userViewModel;
    private SettingAdapter adapter;

    private ArrayList<String> friendlist= new ArrayList<>();;



    public static SettingFragmentFriend newInstance() {
        return new SettingFragmentFriend();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_friend,container,false);
        View view = binding.getRoot();
        Context context = view.getContext();


        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_listView);
        mRecyclerView = view.findViewById(R.id.setting_listView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        userViewModel= new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.getLiveUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                friendlist.addAll(user.getFriendList());
                adapter = new SettingAdapter(context,friendlist);
                mRecyclerView.setAdapter(adapter);
            }
        });
        
        return view;
    }
}
