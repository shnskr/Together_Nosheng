package com.together.nosheng;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.databinding.ActivityMainBinding;
import com.together.nosheng.view.LoginActivity;
import com.together.nosheng.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        if (userViewModel.firebaseUser.getValue() == null) {
            Intent intetn = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intetn);
        } else {
            binding.tvUid.setText(userViewModel.firebaseUser.getValue().getUid());
            binding.tvEmail.setText(userViewModel.firebaseUser.getValue().getEmail());
        }

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                Intent intetn = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intetn);
            }});

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
