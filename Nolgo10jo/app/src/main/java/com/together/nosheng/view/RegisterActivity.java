package com.together.nosheng.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.together.nosheng.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { signUp(); }
        });
    }

    private void signUp() {
        String email = binding.email.getText().toString();
        String pw = binding.regPasswd.getText().toString();
        String pwcheck = binding.regPasswdcheck.getText().toString();

        if(email.length() > 0 && pw.length()>0 && pwcheck.length()>0) {
            if(pw.equals(pwcheck)){
                firebaseAuth.createUserWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
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


}
