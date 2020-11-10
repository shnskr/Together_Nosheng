package com.together.nosheng;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PasswordResetActivity extends AppCompatActivity {
    private Button info_submit;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        info_submit = (Button) findViewById(R.id.Info_Submit);

        firebaseAuth = firebaseAuth.getInstance();

        info_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpdate();
            }
        });

    }

    private void startMyActivity(Class c){
        Intent intent = new Intent (PasswordResetActivity.this, c);
        startActivity(intent);
    }

    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_LONG).show();}

    private void userUpdate () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startToast("업데이트 완료");
                        }
                    }
                });
    }
}
