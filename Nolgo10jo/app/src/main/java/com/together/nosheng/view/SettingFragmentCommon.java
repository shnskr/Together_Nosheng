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
import com.together.nosheng.databinding.SettingFragmentCommonBinding;


public class SettingFragmentCommon extends Fragment {
    private SettingFragmentCommonBinding binding;

    public static SettingFragmentCommon newInstance() {
        return new SettingFragmentCommon();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_common,container,false);
        View view = binding.getRoot();
        return view;



    }
}
