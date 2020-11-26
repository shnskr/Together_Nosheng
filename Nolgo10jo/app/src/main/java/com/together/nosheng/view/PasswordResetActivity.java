package com.together.nosheng.view;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityPasswordResetBinding;

public class PasswordResetActivity extends AppCompatActivity {

    private ActivityPasswordResetBinding binding;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.pwReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPW();
            }
        });

    }

    private void resetPW () {
        String emailAddress = ((EditText)findViewById(R.id.email_reset)).getText().toString();

        firebaseAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordResetActivity.this, "재설정 이메일 전송 완료", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
