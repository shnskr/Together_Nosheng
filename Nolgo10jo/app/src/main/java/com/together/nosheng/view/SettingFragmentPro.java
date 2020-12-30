package com.together.nosheng.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.together.nosheng.util.GlobalApplication;
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

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalApplication.logout();
                startActivity(new Intent(getContext(), LoginActivity.class));
                requireActivity().finish();
            }
        });

        if (GlobalApplication.firebaseUser.getUid().equals("OfItgRJq0ZXQllJIFe5Zn9jrTZ73")) {//dal@dal.dal 이메일일 경우(관리자 계정)
            binding.btnGoadmin.setVisibility(View.VISIBLE);                                 //btnGoadmin버튼이 활성화

            binding.btnGoadmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().startActivity(new Intent(requireActivity(), AdminUserActivity.class));
                }
            });
        }

        return root;


    }

    private void changenickName() {
        EditText et = new EditText(requireContext());
        et.setLines(1);
        et.setHint("닉네임을 입력 해주세요.");
        et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et.removeTextChangedListener(this);

                if (s.length() > 8) {
                    et.setText(s.subSequence(0, 8));
                } else {
                    et.setText(s.toString().replaceAll(" ", ""));
                }
                et.setSelection(et.length());

                et.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder dlg = new AlertDialog.Builder(requireContext());
        dlg.setTitle("닉네임 변경");
        dlg.setMessage("최대 8글자까지 가능합니다.");
        dlg.setView(et);
        dlg.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nickName = et.getText().toString();
                if (nickName.length() < 1) {
                    Toast.makeText(requireContext(), "닉네임을 입력 해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    userViewModel.changeNickname(nickName);
                }
            }
        });
        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.show();
    }


}
