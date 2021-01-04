package com.together.nosheng.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.together.nosheng.R;
import com.together.nosheng.databinding.SettingFragmentProBinding;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;

public class SettingFragmentPro extends Fragment {
    private UserViewModel userViewModel;

    private Boolean isPermission = true;

    private FirebaseStorage storage;
    private StorageReference storageRef;


    public static SettingFragmentPro newInstance() {
        return new SettingFragmentPro();
    }

    private SettingFragmentProBinding binding;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_pro, container, false);
        View root = binding.getRoot();

        context = requireContext();

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setLiveUser();

        userViewModel.getLiveUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.txtUsername.setText(user.getNickName());
                binding.txtUseremail.setText(user.geteMail());


                binding.ivAdmi.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isPermission) goToAlbum();
                        else
                            Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();

                        tedPermission();
                    }
                });

                storage = FirebaseStorage.getInstance();
                storageRef = storage.getReference();

                if (user.getThumbnail().equals("")) {//썸네일이 null일때 기본이미지 출력
                    storageRef.child("/user/iv_test.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null) {
                                Glide.with(context)
                                        .load(uri)
                                        .into(binding.ivAdmi);
                            }
                        }
                    });
                } else {//유저 설정 썸네일 출력
                    // AdminUserActivity / onComplete 함수안에서 document.getId를 했기때문에 바로 user.getThumbnail해도 댐.
                    storageRef.child("/user/" + GlobalApplication.firebaseUser.getUid() + "/" + user.getThumbnail()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //이미지 로드 성공시
                            if (uri != null) {
                                Glide.with(context)
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
                startActivity(new Intent(context, LoginActivity.class));
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
        EditText et = new EditText(context);
        et.setLines(1);
        et.setHint("닉네임을 입력 해주세요.");
        et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        et.setSingleLine();
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et.removeTextChangedListener(this);

                if (s.length() > 6) {
                    et.setText(s.subSequence(0, 6));
                }
                et.setSelection(et.length());

                et.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder dlg = new AlertDialog.Builder(context);

        dlg.setTitle("닉네임 변경");
        dlg.setMessage("최대 6글자까지 가능합니다.");
        dlg.setView(et);
        dlg.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nickName = et.getText().toString();
                if (nickName.length() < 1) {
                    Toast.makeText(context, "닉네임을 입력 해주세요.", Toast.LENGTH_SHORT).show();
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

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);


    }


    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(requireActivity())
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

}

