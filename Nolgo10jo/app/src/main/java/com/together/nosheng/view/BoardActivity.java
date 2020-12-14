package com.together.nosheng.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.together.nosheng.R;
import com.together.nosheng.adapter.BoardAdapter;
import com.together.nosheng.databinding.ActivityAdminUserBinding;
import com.together.nosheng.databinding.ActivityBoardBinding;
import com.together.nosheng.model.board.Board;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityBoardBinding binding;

    private BoardAdapter.RecyclerViewClickListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView rv;
    private BoardAdapter mAdapter;
    private List<Board> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnClickListener();

        binding.mainBoardEdit.setOnClickListener(this);
    }//oncreate

    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        Board board = new Board();
        db.collection("AdminBoard")
//                .orderBy(board.getDate(), Query.Direction.ASCENDING);

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
                    mAdapter = new BoardAdapter(mDatas,listener, BoardActivity.this);
                    binding.mainRecyclerview.setAdapter(mAdapter);
                    binding.mainRecyclerview.addItemDecoration(new RecyclerViewDecoration(6));
                }
            }
        });
    }

    private void setOnClickListener(){
        listener = new BoardAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {//아이템 상세페이지로 이동
                Intent intent = new Intent(getApplicationContext(), ItemViewActivity.class);
                intent.putExtra("title",mDatas.get(position).getTitle());
                intent.putExtra("contents",mDatas.get(position).getContents());
                intent.putExtra("time",mDatas.get(position).getDate());
                intent.putExtra("documentId",mDatas.get(position).getDocId());
                startActivity(intent);
            }
        };
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(BoardActivity.this,BoardWriteActivity.class));
    }
}
