package com.together.nosheng.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.together.nosheng.databinding.ActivityRegisterBinding;
import com.together.nosheng.model.user.User;

import java.util.ArrayList;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { signUp(); }
        });
    }

    private void signUp() {
        String email = binding.email.getText().toString();
        String pw = binding.regPasswd.getText().toString();
        String pwcheck = binding.regPasswdcheck.getText().toString();
        String nickName = binding.etNickName.getText().toString();

        if(email.length() > 0 && pw.length()>0 && pwcheck.length()>0 && nickName.length() > 0) {
            if(pw.equals(pwcheck)){
                firebaseAuth.createUserWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    User user = new User();
                                    user.seteMail(email);
                                    user.setNickName(nickName);
                                    user.setProjectList(new ArrayList<>());
                                    user.setFriendList(new ArrayList<>());
                                    user.setRegDate(new Date());

                                    addUser(task.getResult().getUser().getUid(), user);

                                    startToast("가입성공");
                                    startMyActivity(LoginActivity.class);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    startToast(task.getException().toString());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } else {
                startToast("패스워드가 일치하지 않습니다");
            }
        }

    }

    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_LONG).show();}
    private void startMyActivity(Class c){
        Intent intent = new Intent (RegisterActivity.this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void addUser(String uid, User user) {
        db.collection("User").document(uid)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("RegisterActivity", "유저 추가 성공 : " + user);
                        } else {
                            Log.i("RegisterActivity", "유저 추가 실패 : " + user);
                        }
                    }
                });
    }

}
