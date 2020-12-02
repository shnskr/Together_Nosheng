package com.together.nosheng.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.together.nosheng.R;
import com.together.nosheng.databinding.SettingFragmentBookmarkBinding;
import com.together.nosheng.databinding.SettingFragmentFriendBinding;

public class SettingFragmentFriend extends Fragment {
    private SettingFragmentFriendBinding binding;

    public static SettingFragmentFriend newInstance() {
        return new SettingFragmentFriend();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_friend,container,false);
        View view = binding.getRoot();
        return view;



    }
}
