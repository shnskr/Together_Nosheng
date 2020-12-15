package com.together.nosheng.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.together.nosheng.adapter.UserListAdapter;
import com.together.nosheng.databinding.ActivityAdminUserBinding;
import com.together.nosheng.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class AdminUserActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAdminUserBinding binding;

    FirebaseFirestore db;
    UserListAdapter userListAdapter = new UserListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("@@성공@@", document.getId() + " => " + document.getData());
                                userListAdapter.addItem(document.toObject(User.class));
                                binding.lvUser.setAdapter(userListAdapter);
                            }
                        } else {
                            Log.w("@@실패!@@", "Error getting documents.", task.getException());
                        }
//                        User user = QueryDocumentSnapshot.get(User.class);
                    }
                });


        binding.btnGoboard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AdminUserActivity.this, BoardActivity.class);
        startActivity(intent);
    }
}