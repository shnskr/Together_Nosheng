package com.together.nosheng.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.together.nosheng.databinding.ActivityItemView2Binding;

public class ItemViewActivity2 extends AppCompatActivity {

    private ActivityItemView2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemView2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("title");
        String contents = getIntent().getStringExtra("contents");
        String date = getIntent().getStringExtra("date");
        String docId = getIntent().getStringExtra("docId");//작성자
//        String documentId = getIntent().getStringExtra("documentId");


        binding.itTitle2.setText(title);
        binding.itContents2.setText(contents);
        binding.itTime2.setText("작성일:" + date);
        binding.itId2.setText("작성자:"+docId);

    }
}