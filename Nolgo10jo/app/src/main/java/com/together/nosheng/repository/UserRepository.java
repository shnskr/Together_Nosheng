package com.together.nosheng.repository;

import android.util.Log;

<<<<<<< HEAD
=======
import androidx.annotation.NonNull;
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

<<<<<<< HEAD
=======
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.user.User;
<<<<<<< HEAD
import com.together.nosheng.viewmodel.UserViewModel;

public class UserRepository {
    private UserViewModel userViewModel;
    private FirebaseFirestore db;
    private MutableLiveData<User> liveUser = new MutableLiveData<>();
    private String TAG = "UserRepository";
    private String userid;
=======
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;

public class UserRepository {
    private String TAG = "UserRepository";

    private FirebaseFirestore db;
    private MutableLiveData<User> liveUser = new MutableLiveData<>();

    private ArrayList<String> userNickName = new ArrayList<>();
    private MutableLiveData<ArrayList<String>> liveUserNickName = new MutableLiveData<ArrayList<String>>();
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c


    public UserRepository() {
        db = FirebaseFirestore.getInstance();
<<<<<<< HEAD
        initPush();
=======
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
    }

    public LiveData<User> findAll() {
        return liveUser;
    }

<<<<<<< HEAD
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
=======

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
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
