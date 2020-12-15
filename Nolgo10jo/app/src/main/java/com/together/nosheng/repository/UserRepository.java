package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.UserViewModel;

public class UserRepository {
    private UserViewModel userViewModel;
    private FirebaseFirestore db;
    private MutableLiveData<User> liveUser = new MutableLiveData<>();
    private String TAG = "UserRepository";
    private String userid;


    public UserRepository() {
        db = FirebaseFirestore.getInstance();
        initPush();
    }

    public LiveData<User> findAll() {
        return liveUser;
    }

    private void initPush() {


        final DocumentReference doRef = db.collection("User").document(userid);
        doRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.w(TAG, "Listen Failed",error);
                    return;
                }
                if (value != null) {
                    Log.w(TAG,"current Data"+ value.getData());
                    liveUser.setValue(value.toObject(User.class));
                }else {
                    Log.d(TAG,"current Data : null");
                }

            }
        });
    }

    public void setUserId(String userId) {
        this.userid  = userId;
    }


    public void changeNickname(String nickname){
        User user = new User(liveUser.getValue().geteMail(), nickname,liveUser.getValue().getRegDate(),liveUser.getValue().getThumbnail());
        db.collection("User").document(userid).set(user,SetOptions.merge());
    }
}
