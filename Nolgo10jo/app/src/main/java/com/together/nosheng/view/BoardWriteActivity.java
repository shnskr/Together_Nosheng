package com.together.nosheng.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.databinding.ActivityBoardWriteBinding;
import com.together.nosheng.model.board.Board;
import com.together.nosheng.model.user.User;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardWriteActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ActivityBoardWriteBinding binding;
    private List<Board> docnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.writeSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

//            String postId = mStore.collection(FirebaseID.post).document().getId();
//        Map<String, Board> data = new HashMap<>();
//            data.put(FirebaseID.writer, writer);//@@@@@@@@@@@@@@@@@@@@@@@@얘만 추가하면 어플이 안돌아간다 왜일까!!@#@!#@!#@!#@!#!
//            data.put(FirebaseID.documentId, mAuth.getCurrentUser().getUid());
//        data.put(BoardID.docId, "admin");//################################
//        data.put(BoardID.title, binding.writeTitleEdit.getText().toString());
//        data.put(BoardID.contents, binding.writeContentsEdit.getText().toString());
//            data.put(BoardID.timestamp, FieldValue.serverTimestamp());
//        data.put(BoardID.timestamp, new Date());
        Log.i("음", "맛있다~");

        Board board = new Board();

        board.setDocId("admin");
        board.setTitle(binding.writeTitleEdit.getText().toString());
        board.setContents(binding.writeContentsEdit.getText().toString());
        board.setDate(new Date());

//        List<Integer> docList = Collections.singletonList(board.getDocNumber());
//        for (int docId : docList) {

            CollectionReference collection = db.collection("AdminBoard");//<문서 아이디 숫자로 주려는 작업>

            collection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            int count = queryDocumentSnapshots.toObjects(Board.class).size();
                            collection.document(String.valueOf(count + 1)).set(board);
                        }
                    });
//                    .document()
//                    .set(board, SetOptions.merge());
            finish();//</문서 아이디 숫자로 주려는 작업>
//        }


//        db.collection("AdminBoard").document().set(board, SetOptions.merge())//여기서 set board를 하기때문에 위에 5줄코드는 onSuccess안으로 안들어가도 댐.
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.i("음", "성공했다~");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.i("음", "실패했다~");
//                        Log.i("음", e + "");
//                    }
//                });
//        finish();
    }

}