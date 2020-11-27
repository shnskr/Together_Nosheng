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


public class SettingFragmentBookmark extends Fragment {
    private SettingFragmentBookmarkBinding binding;

    public static SettingFragmentBookmark newInstance() {
        return new SettingFragmentBookmark();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_bookmark,container,false);
        View view = binding.getRoot();
        return view;



    }
}
