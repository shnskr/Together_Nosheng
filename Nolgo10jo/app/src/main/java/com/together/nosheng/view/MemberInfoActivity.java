package com.together.nosheng.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.together.nosheng.databinding.ActivityMemberInfoBinding;

public class MemberInfoActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private ActivityMemberInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = firebaseAuth.getInstance();

        binding.InfoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { profileUpdate(); }
        });
    }

    private void profileUpdate() {
        String name = binding.UserName.getText().toString();

        if(name.length()>0) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Jane Q. User")
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MemberInfoActivity.this, "업데이트 완료", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else {
            Toast.makeText(MemberInfoActivity.this, "이름을 입력해주세요", Toast.LENGTH_LONG).show();
        }
    }

}


