package com.together.nosheng.view;
<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
=======
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
<<<<<<< HEAD
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
=======
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
import com.together.nosheng.R;
import com.together.nosheng.adapter.HomeAdapter;
import com.together.nosheng.databinding.ActivityMainBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private UserViewModel userViewModel;
    private ProjectViewModel projectViewModel;

    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

<<<<<<< HEAD

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        if (userViewModel.firebaseUser.getValue() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


=======
        if (GlobalApplication.firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            BottomNavigationView bottomNavigationView = findViewById(binding.bottomNavigation.getId());
            bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c

            bottomNavigationView.setSelectedItemId(R.id.nav_home);

            binding.bottomNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NewTripActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
<<<<<<< HEAD
        fragmentTransaction.replace(R.id.framelaout2,fragment).commit();
=======
        fragmentTransaction.replace(R.id.framelaout2, fragment).commit();
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragmentActivity();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragmentActivity();
                            break;
<<<<<<< HEAD
                        case R.id.nav_setting :
=======
                        case R.id.nav_setting:
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
                            selectedFragment = SettingFragment.newInstance();
                            //selectedFragment = new SettingFragmentActivity();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };
}
