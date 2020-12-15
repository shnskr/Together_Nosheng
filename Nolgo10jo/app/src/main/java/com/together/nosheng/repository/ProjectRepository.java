package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRepository {

    private String TAG = "ProjectRepository";

    private FirebaseFirestore db;
    private MutableLiveData<Map<String, Project>> currentProject = new MutableLiveData<>();
    private MutableLiveData<Map<String, Project>> userProject = new MutableLiveData<>();
    private MutableLiveData<List<Post>> projectPosts = new MutableLiveData<>();

    private String userId;
    private List<Post> posts = new ArrayList<>();

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    //기존 date Datepicker에 표시
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


    //로그인한 user에게 project 추가
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

    //user project update - 전체 필드 set
    public void updateUserProject(Project userProject, String projectId) {
        db.collection("Project").document(projectId)
                .set(userProject, SetOptions.merge());
    }

    //Datepicker date db 저장
    public void datepickerUpdate() {
        db.collection("Project").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("TAG", "addShapshotListener failed", error);
                    return;
                }

                if (value != null) {
                    Log.w(TAG, "여기가 되나안되나 함 봅시다 : " + value.getDocuments());
                    value.toObjects(Project.class);
//                    liveProject.setValue(document.add);
                }
            }
        });
    }

    //로그인된 사용자 프로젝트 가져오기.
    public MutableLiveData<Map<String, Project>> getUserProject() {
        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }
                if (value != null) {
                    List<String> projectList = value.toObject(User.class).getProjectList();

                    for (String projectId : projectList) {
                        db.collection("Project").document(projectId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        if (task.isSuccessful()) {
                                            if (document.exists()) {
                                                Map<String, Project> temp = new HashMap<>();
                                                temp.put(projectId, document.toObject(Project.class));
                                                Log.i(TAG, projectId);
                                                userProject.setValue(temp);
                                            }
                                        } else {
                                            Log.i(TAG, "유저 프로젝트가 null임");
                                        }
                                    }
                                });
                    }
                }
            }
        });
        return userProject;
    }

    //현재 진입한 프로젝트
    public MutableLiveData<Map<String, Project>> getCurrentProject(String projectId) {
        db.collection("Project").document(projectId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }

                if (value != null && value.exists()) {
                    Map<String, Project> currentProject_ = new HashMap<>();
                    currentProject_.put(projectId, value.toObject(Project.class));
                    currentProject.setValue(currentProject_);
                }
            }
        });
        return currentProject;
    }

    //post list
    public MutableLiveData<List<Post>> getProjectPosts(String projectId) {
        db.collection("Project").document(projectId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "add SnapshotListener failed", error);
                            return;
                        }
                        if (value != null) {
                            List<Post> postList = value.toObject(Project.class).getPosts();

                            for (int i = 0; postList.size() >= i; i++) {
                                db.collection("Project").document(projectId).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                if (task.isSuccessful()) {
                                                    if (documentSnapshot.exists()) {
                                                        List<Post> temp = new ArrayList<>();
                                                        temp.add(documentSnapshot.toObject(Post.class));
                                                        Log.i(TAG, projectId);
                                                        projectPosts.setValue(temp);
                                                    } else {
                                                        Log.i(TAG, "Project Repository is null");
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
        return projectPosts;
    }

    //새로 작성한 post 올리기
    public void addPost(String projectId, List<Post> posts){
        DocumentReference doc = db.collection("Project").document(projectId);

        doc.update("posts", posts)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Success adding new Post");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding new Post", e);
                    }
                });
    }

}   //end class