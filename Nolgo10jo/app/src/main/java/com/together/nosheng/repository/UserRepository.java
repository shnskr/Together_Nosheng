package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private String TAG = "UserRepository";

    private FirebaseFirestore db;

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<User> getLiveUser() {
        MutableLiveData<User> liveUser = new MutableLiveData<>();
        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error);
                    return;
                }
                if (value != null) {
                    Log.w(TAG, "current Data" + value.getData());
                    liveUser.setValue(value.toObject(User.class));
                } else {
                    Log.d(TAG, "current Data : null");
                }
            }
        });
        return liveUser;
    }


    public void changeNickname(String nickName) {
        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).update("nickName", nickName);
    }

    public MutableLiveData<List<User>> getUserFriendList() {
        MutableLiveData<List<User>> userFriendList = new MutableLiveData<>();
        List<User> friendList = new ArrayList<>();

        //유저 아이디를 통해서 유저 친구 목록 받아오기
        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }
                if (value != null) {
                    friendList.clear();
                    userFriendList.setValue(friendList);
                    List<String> friendListId = value.toObject(User.class).getFriendList();
                    for (String userId : friendListId) {
                        db.collection("User").document(userId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        if (task.isSuccessful() && document.exists()) {
                                            Log.i(TAG, "성공! : " + userId);
                                            friendList.add(document.toObject(User.class));
                                            userFriendList.setValue(friendList);
                                        }
                                    }
                                });
                    }
                }
            }
        });
        return userFriendList;
    }

    public void updateUserProjectList(String uid, List<String> projectList) {
        db.collection("User").document(uid)
                .update("projectList", projectList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "updateUserProjectList is successfull");
                        } else {
                            Log.i(TAG, "update user project list error");
                        }
                    }
                });
    }

    public List<String> getMemberProject(String member) {
        List<String> userProjectList = new ArrayList<>();
        Log.i(TAG + " getMemberProject:::성공!", member);
        db.collection("User").document(member)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        List<String> temp = task.getResult().toObject(User.class).getProjectList();
                        userProjectList.addAll(temp);
                        Log.i(TAG + " getMemberProject::성공!", userProjectList.toString());
                    } else {
                        Log.i(TAG + " 비회원!", userProjectList.toString());
                    }
                } else {
                    Log.i(TAG, "Error getting user projectList");
                }
            }
        });
        return userProjectList;
    }

    public MutableLiveData<List<String>> getUserNickName(List<String> members) {
        MutableLiveData<List<String>> mutableLiveData = new MutableLiveData<>();
        List<String> userNickNames = new ArrayList<>();
        Log.i(TAG + " 성공0", " members : " + members);
        for (String member : members) {
            db.collection("User").document(member)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.w(TAG, "addShapshotListener failed", error);
                            }
                            if (value != null) {
                                Log.i(TAG + " value", " value : " + value);
                                User user = value.toObject(User.class);
                                Log.i(TAG + " 성공2", " userNickNames : " + user);
                                if (user != null) {
                                    userNickNames.add(user.getNickName());
                                } else {
                                    userNickNames.add(member);
                                    Log.i(TAG + " 성공3", " userNickNames : " + userNickNames);
                                }
                                Log.i(TAG + " 성공4", " userNickNames : " + user);
                                mutableLiveData.setValue(userNickNames);
                            }
                        }
                    });
        }
        return mutableLiveData;
    }

    public void updateUserBookmarList(String planId, List<String> bookmarkList) {
        if (bookmarkList.contains(planId)) {
            bookmarkList.remove(planId);
        } else {
            bookmarkList.add(planId);
        }

        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).update("bookmarkList", bookmarkList);
    }

    public void deleteProject(String projectId) {
        DocumentReference documentReference = db.collection("User").document(GlobalApplication.firebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                if (task.isSuccessful() && doc.exists()) {
                    List<String> projectList = doc.toObject(User.class).getProjectList();

                    projectList.remove(projectId);

                    documentReference.update("projectList", projectList);
                }
            }
        });
    }
}