package com.together.nosheng.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.together.nosheng.adapter.HomeAdapter;
import com.together.nosheng.adapter.UserListAdapter;
import com.together.nosheng.adapter.UserProjectListAdapter;
import com.together.nosheng.databinding.ActivityAdminUserBinding;
import com.together.nosheng.databinding.ActivityAuserProjectListBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuserProjectListActivity extends AppCompatActivity {


    private ActivityAuserProjectListBinding listBinding;

    private String TAG = "AuserProjectListActivity";

    private ListView listView;
    private FirebaseFirestore db;
    private Map<String, Project> userProjectMapp;
    ArrayList<String> ids = new ArrayList<>();


    private Context context;


    UserProjectListAdapter userProjectListAdapter = new UserProjectListAdapter(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBinding = ActivityAuserProjectListBinding.inflate(getLayoutInflater());
        setContentView(listBinding.getRoot());

        db = FirebaseFirestore.getInstance();

        String docId = getIntent().getStringExtra("docId");//intent로 넘긴 docId를 받아서 밑에 document(docId)부분에 쓰면되용>ㅁ<

        db.collection("User").document(docId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.w(TAG, "addShapshotListener failed",error);
                    return;
                }
                if (value != null){
//                    userProjectMapp = new HashMap<>();
                    List<String> aProjectList = value.toObject(User.class).getProjectList();

                    for (String aProjectId : aProjectList){
                        db.collection("Project").document(aProjectId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        if (task.isSuccessful()){
                                            if (document.exists()){
//                                                userProjectMapp.put(aProjectId, document.toObject(Project.class));
                                                Log.i(TAG, aProjectId);
                                                UserProjectListAdapter adapter = new UserProjectListAdapter(AuserProjectListActivity.this);
                                                adapter.addItem(document.toObject(Project.class), aProjectId);
                                                listBinding.aUserProjectList.setAdapter(adapter);
                                            }
                                        }else {
                                            Log.i(TAG, "유저 프로젝트가 null임");
                                        }
                                    }
                                });
                    }
                }
            }
        });



        listBinding.aUserProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//리스트 아이템 클릭은 일단 나중에 @_@
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(context , AuserProjectListActivity.class);
//                intent.putExtra("docId", ids.get(position));
//                context.startActivity(intent);
            }
        });



    }//oncreate
}