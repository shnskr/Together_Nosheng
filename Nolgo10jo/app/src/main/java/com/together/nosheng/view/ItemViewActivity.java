package com.together.nosheng.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.together.nosheng.adapter.BoardAdapter;
import com.together.nosheng.databinding.ActivityBoardBinding;
import com.together.nosheng.databinding.ActivityItemViewBinding;
import com.together.nosheng.model.board.Board;

import java.text.SimpleDateFormat;
import java.util.List;

public class ItemViewActivity extends AppCompatActivity{

    private ActivityItemViewBinding binding;

    private BoardAdapter mAdapter;
    private List<Board> datas;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String title = getIntent().getStringExtra("title");
        String contents = getIntent().getStringExtra("contents");
        String date = getIntent().getStringExtra("date");
        String documentId = getIntent().getStringExtra("documentId");

        binding.itTitle.setText(title);
        binding.itContents.setText(contents);
        binding.itTime.setText("작성일 : " + date);


        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Board data = datas.get(position);
                Intent intent = new Intent(ItemViewActivity.this, BoardEditActivity.class);

                intent.putExtra("title", title);
                intent.putExtra("contents", contents);

                intent.putExtra("documentId", documentId);

                startActivity(intent);
            }
        });

    }



}