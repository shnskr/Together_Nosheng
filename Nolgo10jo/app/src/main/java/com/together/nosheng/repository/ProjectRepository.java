package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.together.nosheng.model.project.Project;

import java.util.ArrayList;

public class ProjectRepository {
    private FirebaseFirestore db;
    private MutableLiveData<ArrayList<Project>> projects = new MutableLiveData<>();

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<ArrayList<Project>> getUserProjectList() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference doRef = db.collection("User").document(userId);

        doRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("ProjectRepository", "Listen failed.", error);
                    return;
                }

                if (value != null && value.exists()) {
                    Log.d("ProjectRepository", "Current data: " + value.getData());
                    ArrayList<String> temp = (ArrayList<String>) value.get("projectList");
                    ArrayList<Project> projects_ = new ArrayList<>();
                    for (String projectId : temp) {
                        db.collection("Project").document(projectId)
                                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (value != null && value.exists()) {
                                            projects_.add(value.toObject(Project.class));
                                        }
                                    }
                                });
                    }
                } else {
                    Log.d("ProjectRepository", "Current data: null");
                }
            }
        });
        return projects;
    }
}
