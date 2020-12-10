package com.together.nosheng.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.databinding.SettingFragmentProBinding;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.UserViewModel;

public class SettingFragmentPro extends Fragment {
    private UserViewModel userViewModel;

    public static SettingFragmentPro newInstance() {
        return new SettingFragmentPro();
    }

    private SettingFragmentProBinding binding;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_pro,container,false);
        View root = binding.getRoot();

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setLiveUser();

        userViewModel.getLiveUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.i("daldal", "변화가 생겨쬬");
                binding.txtUsername.setText(user.getNickName());
                binding.txtUseremail.setText(user.geteMail());
            }
        });

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changenickName();
            }
        });

        return root;



    }

    private void changenickName() {
        final EditText et = new EditText(getContext());
        FrameLayout container = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        et.setLayoutParams(params);
        container.addView(et);
        final AlertDialog.Builder alt_bld = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
        alt_bld.setTitle("이름 변경")
                .setMessage("변경할 이름을 입력하세요.")
                .setIcon(R.drawable.ic_baseline_create_24)
                .setCancelable(false)
                .setView(container)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = et.getText().toString();
                        if (value.length() != 0) {
                            userViewModel.changeNickname(value);
                        }else {
                            Toast.makeText(getActivity(),"값이 없습니다.",Toast.LENGTH_LONG).show();
                        }

                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }


}
