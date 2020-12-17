package com.together.nosheng.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.together.nosheng.databinding.LayoutSearchItemDetailBinding;

public class SearchPlanDetailActivity extends AppCompatActivity {
    private LayoutSearchItemDetailBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutSearchItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("Title");
        String theme = getIntent().getStringExtra("Theme");
        String like = getIntent().getStringExtra("Like");
        //String date = getIntent().getStringExtra("Date");

        binding.searchDetailTitle.setText(title);
        binding.searchDetailTheme.setText(theme);
        binding.searchDetailLike.setText(like);
        //binding.searchDetailDate.setText(date);


    }
}
