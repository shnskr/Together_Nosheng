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
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private String TAG = "UserRepository";

    private FirebaseFirestore db;
    private MutableLiveData<User> liveUser = new MutableLiveData<>();

    private ArrayList<String> userNickName = new ArrayList<>();
    private MutableLiveData<ArrayList<String>> liveUserNickName = new MutableLiveData<ArrayList<String>>();
    private MutableLiveData<List<String>> friendList = new MutableLiveData<>();
    private MutableLiveData<Map<String,User>> userFriendList = new MutableLiveData<>();

    private MutableLiveData<List<User>> userFriendList2 = new MutableLiveData<>();
    private List<User> userFriendListHolder = new ArrayList<>();
    private List<String> userProjectList = new ArrayList<>();
//    private User temp;


    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<User> findAll() {
        return liveUser;
    }


    public MutableLiveData<ArrayList<String>> findFriend() {
        return liveUserNickName;
    }

    public void setFriend(ArrayList<String> lists) {
        int count = lists.size();
        Log.d(TAG,"ListSize : " +lists.size());


        for (String id : lists) {
            final DocumentReference doRef = db.collection("User").document(id);
            doRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {


                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    User user = value.toObject(User.class);
                    userNickName.add(user.getNickName());
                    liveUserNickName.setValue(userNickName);
                    Log.d(TAG,"Override In" + liveUserNickName.getValue());
                }
            });
        }
    }



    public LiveData<User> getLiveUser() {
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



    public void changeNickname(User user){
        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).set(user,SetOptions.merge());
    }

    //친구 목록 관련 코드
    public LiveData<List<String>> getFriendList() {
            List<String> temp = liveUser.getValue().getFriendList();
            friendList.setValue(temp);
            return friendList;
    }

    public LiveData<List<User>> getUserFriendList() {

        //유저 아이디를 통해서 유저 친구 목록 받아오기
        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }
                if(value != null){
                    List<String> friendList = value.toObject(User.class).getFriendList();
                    for(String userId : friendList){
                        db.collection("User").document(userId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        if(task.isSuccessful()){
                                            if(document.exists()){
//                                                User temp;
//                                                temp =document.toObject(User.class);
                                                userFriendListHolder.add(document.toObject(User.class));
                                                Log.i(TAG, "성공! : "+userId);
                                                userFriendList2.setValue(userFriendListHolder);
                                            }
                                        }else {
                                            Log.i(TAG," 친구가 없낭");
                                        }
                                    }
                                });
                    }
                }
            }
        });

//        db.collection("User").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//            if(error != null) {
//                Log.w(TAG, "addSnapshotListener failed");
//                return;
//            }
//
//            if(value != null && value.exists()) {
//                Map<String,User> temp = new HashMap<>();
//                temp.put(userId, value.toObject(User.class));
//                userFriendList.setValue(temp);
//            }
//            }
//        });
        return userFriendList2;
    }

    public void updateUserProjectList(List<String> projectList) {
        db.collection("User").document(GlobalApplication.firebaseUser.getUid())
                .update("projectList", projectList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.i(TAG, "updateUserProjectList is successfull");
                        } else {
                            Log.i(TAG, "update user project list error");
                        }
                    }
                });
    }

    public List<String> getUserProject() {
        db.collection("User").document(GlobalApplication.firebaseUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    List<String> temp = task.getResult().toObject(User.class).getProjectList();
                    userProjectList.addAll(temp);
                    Log.i(TAG+" 성공!",userProjectList.toString());
                } else {
                    Log.i(TAG, "Error getting user projectList");
                }
            }
        });
        return userProjectList;
    }

}