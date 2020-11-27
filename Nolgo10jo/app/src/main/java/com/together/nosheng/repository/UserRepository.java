package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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



    public UserRepository() {
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public UserRepository(String userId) {
        db = FirebaseFirestore.getInstance();
        initPush(userId);
    }




    public LiveData<User> findAll() {
        return liveUser;
    }

    public void initPush(String userId) {


        final DocumentReference doRef = db.collection("User").document(userId);
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

    public ArrayList<String> searchFriend(ArrayList<String> friendId) {
        ArrayList<String> friendList = new ArrayList<>();

        final DocumentReference doRef = db.collection("User").document(userId);

        for (int i=0; i<friendId.size();i++) {
            DocumentReference doRef2 = db.collection("User").document(friendId.get(i));
            doRef2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    String nickName = value.getString("nickName");
                    
                }
            });
        }

        return friendList;

    }



    public void changeNickname(String nickname){
        User user = new User(liveUser.getValue().geteMail(), nickname,liveUser.getValue().getRegDate(),liveUser.getValue().getThumbnail(),liveUser.getValue().getFriendList());
        db.collection("User").document("eSjNTPFLIKSbBeeYpco6xVCypDt1").set(user,SetOptions.merge());
    }
}
