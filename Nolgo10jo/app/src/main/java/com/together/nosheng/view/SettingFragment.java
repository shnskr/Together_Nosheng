package com.together.nosheng.view;

<<<<<<< HEAD
=======
import android.content.Context;
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentSettingBinding;

public class SettingFragment extends Fragment {


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    private ActivityFragmentSettingBinding binding;
    private View view;
<<<<<<< HEAD
=======
    private FragmentTransaction transaction ;



>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //view = inflater.inflate(R.layout.activity_fragment_setting,container,false);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_setting,container,false);
        View root = binding.getRoot();
<<<<<<< HEAD
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        SettingFragmentPro pro = new SettingFragmentPro();
        transaction.replace(R.id.framelaout2,pro);
        transaction.commit();
        binding.btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragmentPro pro = new SettingFragmentPro();
                ((MainActivity)getActivity()).replaceFragment(SettingFragmentPro.newInstance());
            }
        });

=======


        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.framelaout2,SettingFragmentPro.newInstance());
        transaction.commit();



        binding.btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(SettingFragmentPro.newInstance());

            }
        });

        binding.btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(SettingFragmentBookmark.newInstance());

            }
        });

        binding.btnFre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(SettingFragmentFriend.newInstance());


            }
        });
        binding.btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(SettingFragmentCommon.newInstance());


            }
        });

        binding.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(SettingFragmentInfo.newInstance());


            }
        });
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
        return root;
    }
}
