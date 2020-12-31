package com.together.nosheng.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();



                if (user.getThumbnail().equals("")){//썸네일이 null일때 기본이미지 출력
                    storageRef.child("/user/iv_test.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null){
                                Glide.with(requireContext())
                                        .load(uri)
                                        .into(binding.ivAdmi);
                            }
                        }
                    });
                }
                else {//유저 설정 썸네일 출력                              // AdminUserActivity / onComplete 함수안에서 document.getId를 했기때문에 바로 user.getThumbnail해도 댐.
                    storageRef.child("/user/" + GlobalApplication.firebaseUser.getUid() + "/" + user.getThumbnail()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //이미지 로드 성공시
                            if (uri != null) {
                                Glide.with(requireContext())
                                        .load(uri)
                                        .into(binding.ivAdmi);
                            }
                        }
                    });
                }
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
