package com.together.nosheng.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

    private RecyclerView mRecyclerView;
    private UserViewModel userViewModel;
    private SettingAdapter adapter;
    private SettingAdapter.SettingRecyclerViewClickListener listener ;

//    private ArrayList<String> friendlist= new ArrayList<>();
//    private Map<String, User> friendlist= new HashMap<>();
    private List<User> friendList = new ArrayList<>();



    public static SettingFragmentFriend newInstance() {
        return new SettingFragmentFriend();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_friend,container,false);
//        binding = SettingFragmentFriendBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Context context = view.getContext();


        //mRecyclerView = (RecyclerView) view.findViewById(R.id.search_listView);
        mRecyclerView = view.findViewById(R.id.setting_listView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        userViewModel= new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.setUserFriendList();

        userViewModel.getUserFriendList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users == null){
                    Toast.makeText(context, "친구 음써ㅜㅜㅜ", Toast.LENGTH_LONG).show();
                }
                    adapter = new SettingAdapter(users, listener, context);
                    binding.settingListView.setAdapter(adapter);

            }
        });

        return view;
    }

    private void setOnClickListener () {
        listener = new SettingAdapter.SettingRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                //일단 요로케만
            }
        };
    }
}
