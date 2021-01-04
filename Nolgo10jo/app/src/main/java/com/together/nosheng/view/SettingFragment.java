package com.together.nosheng.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentSettingBinding;
import com.together.nosheng.viewmodel.UserViewModel;

public class SettingFragment extends Fragment {

    private ActivityFragmentSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getChildFragmentManager().beginTransaction().replace(R.id.framelaout2, SettingFragmentPro.newInstance()).commit();

        binding.btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.framelaout2, SettingFragmentPro.newInstance()).commit();
            }
        });

        binding.btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.framelaout2, SettingFragmentBookmark.newInstance()).commit();
            }
        });

        binding.btnFre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.framelaout2, SettingFragmentFriend.newInstance()).commit();
            }
        });

        return view;
    }
}
