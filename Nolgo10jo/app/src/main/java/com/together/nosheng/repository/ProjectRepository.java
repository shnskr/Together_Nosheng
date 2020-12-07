package com.together.nosheng.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRepository {
    private FirebaseFirestore db;

    private ProjectViewModel projectViewModel;
//    private MutableLiveData<Project> liveProject = new MutableLiveData<>();
    private MutableLiveData<Map<String, Project>> currentProject= new MutableLiveData<>();
    private String TAG = "ProjectRepository";

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<Map<String, Project>> getDatepicker(String projectId) {
        DocumentReference docRef = db.collection("Project").document(projectId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Project> test = new HashMap<>();
                        test.put(projectId, document.toObject(Project.class));
                        currentProject.setValue(test);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return currentProject;
    }



    public void addUserProject(Project userProject) {
        db.collection("Project")
                .add(userProject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "Success adding document : " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void updateUserProject(Project userProject, String projectId) {
        db.collection("Project").document(projectId)
                .set(userProject, SetOptions.merge());
    }



    public void datepickerUpdate(){
        db.collection("Project").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("TAG", "addShapshotListener failed", error);
                    return;
                }

                if(value != null) {
                    Log.w(TAG, "여기가 되나안되나 함 봅시다 : "+ value.getDocuments());
                    value.toObjects(Project.class);
//                    liveProject.setValue(document.add);
                }
            }
        });
    }
}   //end class