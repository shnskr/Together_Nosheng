package com.together.nosheng.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;

import com.together.nosheng.databinding.ActivityBoardBinding;
import com.together.nosheng.databinding.ActivityItemViewBinding;
import com.together.nosheng.model.board.Board;

import java.text.SimpleDateFormat;

public class ItemViewActivity extends AppCompatActivity {

    private ActivityItemViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("title");
        String contents = getIntent().getStringExtra("contents");
        String date = getIntent().getStringExtra("date");
        String docId = getIntent().getStringExtra("docId");

        //밑에 extras에 title,content가 없을때 출력댐
//        String title = "no title";
//        String contents = "no content";
//        String date = "no date";
//        String  docId = "no docId";


//        Bundle extras = getIntent().getExtras();
//        if (extras != null){
//            title = extras.getString("title");
//            contents = extras.getString("contents");
//            date = extras.getString("date");
//            docId = extras.getString("docId");
//        }

        binding.itTitle.setText(title);
        binding.itContents.setText(contents);
        binding.itTime.setText(date);
        binding.itId.setText(docId);

    }
}