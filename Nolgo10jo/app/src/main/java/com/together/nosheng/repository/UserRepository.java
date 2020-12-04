package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;

public class UserRepository {
    private UserViewModel userViewModel;
    private FirebaseFirestore db;
    private MutableLiveData<User> liveUser = new MutableLiveData<>();
    private String TAG = "UserRepository";
    private static String userId;

    private ArrayList<String> userNickName = new ArrayList<>();
    private MutableLiveData<ArrayList<String>> liveUserNickName = new MutableLiveData<ArrayList<String>>();


    public UserRepository() {
        db = FirebaseFirestore.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
    }

    public UserRepository(String userId) {
        this.userId = userId;
        db = FirebaseFirestore.getInstance();
        initPush(userId);
    }





    public LiveData<User> findAll() {
        return liveUser;
    }


    public MutableLiveData<ArrayList<String>> findFriend() {
        return liveUserNickName;
    }

    public void initPush(String userId) {

        if (userId != null) {

            final DocumentReference doRef = db.collection("User").document(userId);
            doRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

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
        }


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



    public LiveData<User> setData(String id) {
        db.collection("User").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                liveUser.setValue(user);
            }
        });

        return liveUser;
    }



    public void changeNickname(String nickname, String id){
        User user = new User(liveUser.getValue().geteMail(), nickname,liveUser.getValue().getRegDate(),liveUser.getValue().getThumbnail(),liveUser.getValue().getFriendList());
        db.collection("User").document(id).set(user,SetOptions.merge());
    }
}