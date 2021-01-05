package com.together.nosheng.view;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.together.nosheng.databinding.ActivityPopupPlanBinding;

public class PopupPlanActivity extends AppCompatActivity {
    private ActivityPopupPlanBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityPopupPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.contents.setText("Hi");

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
