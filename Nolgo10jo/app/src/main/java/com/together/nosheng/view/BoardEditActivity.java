package com.together.nosheng.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityBoardEditBinding;
import com.together.nosheng.model.board.Board;

public class BoardEditActivity extends AppCompatActivity {

    private ActivityBoardEditBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String title = getIntent().getStringExtra("title");
        String contents = getIntent().getStringExtra("contents");
        //String 들은 Board데이터 불러온거셈
        String documentId = getIntent().getStringExtra("documentId");

        binding.titleEt.setText(title);
        binding.contentsEt.setText(contents);



    binding.btnEditend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Board board = new Board();
            board.setTitle(binding.titleEt.getText().toString());//intent로 넘기는게 아니라 board에 담아서 넘기는거에유
            board.setContents(binding.contentsEt.getText().toString());//왜냐면 BoardActivity에 이미 firestore에서 데이터 받아와서 뿌려주는 동작이 존재하기 때문에?

            db.collection("AdminBoard").document(documentId).set(board, SetOptions.merge());
                                                            //같은 onCreate안에 있어야 documentId라고 한번에 부를수 잇음
            Intent intent = new Intent(BoardEditActivity.this, BoardActivity.class);
            startActivity(intent);
            finish();
        }
    });

    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(BoardEditActivity.this, BoardActivity.class);
////        intent.putExtra("title",getTitle());
////        intent.putExtra("contents",contents)
//        Board data = new Board();
//        db.collection("AdminBoard").document(data.getDocumentId()).set(data, SetOptions.merge());
//        finish();
//        startActivity(intent);
//    }
}