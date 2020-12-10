package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;

public class UserRepository {
    private String TAG = "UserRepository";

    private FirebaseFirestore db;
    private MutableLiveData<User> liveUser = new MutableLiveData<>();

    private ArrayList<String> userNickName = new ArrayList<>();
    private MutableLiveData<ArrayList<String>> liveUserNickName = new MutableLiveData<ArrayList<String>>();


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
}