package com.together.nosheng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.view.LoginActivity;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { signUp(); }
        });
    }

    private void signUp() {
        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        String pw = ((EditText)findViewById(R.id.reg_passwd)).getText().toString();
        String pwcheck = ((EditText)findViewById(R.id.reg_passwdcheck)).getText().toString();

        if(email.length() > 0 && pw.length()>0 && pwcheck.length()>0) {
            if(pw.equals(pwcheck)){
                mAuth.createUserWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startToast("가입성공");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startMyActivity(LoginActivity.class);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    startToast(task.getException().toString());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
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
