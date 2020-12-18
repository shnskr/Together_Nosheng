package com.together.nosheng.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.together.nosheng.R;
import com.together.nosheng.adapter.UserListAdapter;
import com.together.nosheng.databinding.ActivityAdminUserBinding;
import com.together.nosheng.model.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdminUserActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAdminUserBinding binding;

    private ListView listView;

    FirebaseFirestore db;
    UserListAdapter userListAdapter = new UserListAdapter();

    private StorageReference mStorageRef;
    private UploadTask uploadTask;
    private List<User> userList;
    private File tempFile;
    private String path;//이미지 경로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        Log.i("start","start");

        //ListView 아이디 : ListView Id
        listView = (ListView)findViewById(R.id.lv_user);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        userList = new ArrayList<>();

//        ImageView iv = findViewById(R.id.iv_admin);

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//아이템 클릭시 해당유저 프로젝트로
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) parent.getItemAtPosition(position);

            }
        });


    }

    @Override
    public void onClick(View v) {//게시판이동
        Intent intent = new Intent(AdminUserActivity.this, BoardActivity.class);
        startActivity(intent);
    }




}