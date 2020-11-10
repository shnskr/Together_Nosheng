package com.together.nosheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MemberInfoActivity extends AppCompatActivity {
    private Button join, login, reset;
    private EditText email_login;
    private EditText pwd_login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        join = (Button) findViewById(R.id.join);
        login = (Button) findViewById(R.id.login);
        reset = (Button) findViewById(R.id.Info_Submit);
        email_login = (EditText) findViewById(R.id.email);
        pwd_login = (EditText) findViewById(R.id.log_password);

        firebaseAuth = firebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getText().toString().trim();
                String pwd = pwd_login.getText().toString().trim();

                if(email.length()>0 && pwd.length()>0) {

                    firebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(MemberInfoActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startToast("로그인 성공!");
                                        startMyActivity(MainActivity.class);
                                    } else {
                                        startToast(task.getException().toString());
                                    }
                                }
                            });
                }else {
                    if(email.length()==0) {
                        startToast("이메일을 입력해주세요");
                    }
                    if(pwd.length()==0){
                        startToast("패스워드를 입력해주세요");
                    }
                }

            }
        });


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyActivity(RegisterActivity.class);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyActivity(PasswordResetActivity.class);
            }
        });


    }

    private void startMyActivity(Class c){
        Intent intent = new Intent (MemberInfoActivity.this, c);
        startActivity(intent);
    }

    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_LONG).show();}
}
