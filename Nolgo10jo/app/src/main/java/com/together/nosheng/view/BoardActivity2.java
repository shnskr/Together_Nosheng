package com.together.nosheng.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.together.nosheng.adapter.BoardAdapter2;
import com.together.nosheng.databinding.ActivityBoard2Binding;
import com.together.nosheng.model.board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardActivity2 extends AppCompatActivity {

    private ActivityBoard2Binding board2Binding;

    private BoardAdapter2.RecyclerViewClickListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private BoardAdapter2 mAdapter;
    private List<Board> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board2Binding = ActivityBoard2Binding.inflate(getLayoutInflater());
        setContentView(board2Binding.getRoot());

        Log.i("승화니", "뀨우우");

        setOnClickListener();

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        Board board = new Board();
        db.collection("AdminBoard")
//                .orderBy(board.getDate(), Query.Direction.ASCENDING);
//                .orderBy("documentId", Query.Direction.ASCENDING)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.w("eeeeeeerrrrrrrrrrroo",error);
                            return;
                        }

                        if (value != null){
                            mDatas.clear();//혹시몰라.
                            for (DocumentSnapshot snap : value.getDocuments()){
                                Map<String, Object> shot = snap.getData();
                                String documentId = snap.getId();
                                Board board = snap.toObject(Board.class);
                                board.setDocumentId(documentId);
                                mDatas.add(board);
                            }
                            mAdapter = new BoardAdapter2(mDatas, BoardActivity2.this);
                            board2Binding.mainRecyclerview2.setAdapter(mAdapter);
                            board2Binding.mainRecyclerview2.addItemDecoration(new RecyclerViewDecoration(3));
                        }
                    }
                });
    }

    private void setOnClickListener(){
        listener = new BoardAdapter2.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {//아이템 상세페이지로 이동
                Intent intent = new Intent(getApplicationContext(), ItemViewActivity2.class);
                intent.putExtra("title",mDatas.get(position).getTitle());
                intent.putExtra("contents",mDatas.get(position).getContents());
                intent.putExtra("time",mDatas.get(position).getDate());
                intent.putExtra("writer",mDatas.get(position).getDocId());

                startActivity(intent);
            }
        };
    }



}