package com.together.nosheng.view;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_pro, container, false);
        View root = binding.getRoot();

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
//                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                        startActivityForResult(intent,1);
                    }
                });

                storage = FirebaseStorage.getInstance();
                storageRef = storage.getReference();


                if (user.getThumbnail().equals("")) {//썸네일이 null일때 기본이미지 출력
                    storageRef.child("/user/iv_test.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null) {
                                Glide.with(requireContext())
                                        .load(uri)
                                        .into(binding.ivAdmi);
                            }
                        }
                    });
                } else {//유저 설정 썸네일 출력                              // AdminUserActivity / onComplete 함수안에서 document.getId를 했기때문에 바로 user.getThumbnail해도 댐.
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


//    //카메라관련 메서드
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != Activity.RESULT_OK) {
//            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
//
//            if (tempFile != null) {
//                if (tempFile.exists()) {
//                    if (tempFile.delete()) {
//                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
//                        tempFile = null;
//                    }
//                }
//            }
//
//            return;
//        }
//
//        if (requestCode == 1) {
//
//
//            Uri photoUri = data.getData();
//            Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);
//
//            Cursor cursor = null;
//
//            try {
//
//                /*
//                 *  Uri 스키마를
//                 *  content:/// 에서 file:/// 로  변경한다.
//                 */
//                String[] proj = {MediaStore.Images.Media.DATA};
//
//                assert photoUri != null;
//                cursor = getContentResolver().query(photoUri, proj, null, null, null);
//
//                assert cursor != null;
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//
//                cursor.moveToFirst();
//
//                tempFile = new File(cursor.getString(column_index));
//
//                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(tempFile));
//
//            } finally {
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//
//            setImage();
//
//        }
//    }


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

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);


        Cursor cursor = null;
        if (intent.getData() != null) {
            Log.i("testing", "당금");

            try {
                Log.i("testing", "당금");
                Uri photoUri = intent.getData();
                binding.ivAdmi.setImageURI(photoUri);
            } catch (Exception e) {
                Log.i("testing", "시금치...");
                e.printStackTrace();
            }


            Log.i("testing", "당금ㅁㅁ///");

            Bundle result = new Bundle();
            result.putString("intent", intent.getData().toString());
            result.putInt("requestCode", 1);
            result.putInt("resultCode", Activity.RESULT_OK);
//        result.putInt("position", position);
            getParentFragmentManager().setFragmentResult("result", result);
        }
        else{
            Log.i("testing", "시금치/.///");
        }
    }


        private void tedPermission () {

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

