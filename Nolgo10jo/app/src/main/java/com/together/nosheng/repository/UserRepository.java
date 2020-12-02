package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

<<<<<<< HEAD
=======
import com.google.firebase.auth.FirebaseAuth;
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.UserViewModel;

<<<<<<< HEAD
=======
import java.util.ArrayList;

>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
public class UserRepository {
    private UserViewModel userViewModel;
    private FirebaseFirestore db;
    private MutableLiveData<User> liveUser = new MutableLiveData<>();
    private String TAG = "UserRepository";
<<<<<<< HEAD
    private String userid;
=======
    private static String userId;

>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d


    public UserRepository() {
        db = FirebaseFirestore.getInstance();
<<<<<<< HEAD
        initPush();
    }

=======
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public UserRepository(String userId) {
        db = FirebaseFirestore.getInstance();
        initPush(userId);
    }




>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
    public LiveData<User> findAll() {
        return liveUser;
    }

<<<<<<< HEAD
    private void initPush() {


        final DocumentReference doRef = db.collection("User").document(userid);
        doRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
=======
    public void initPush(String userId) {


        final DocumentReference doRef = db.collection("User").document(userId);
        doRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
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

<<<<<<< HEAD
    public void setUserId(String userId) {
        this.userid  = userId;
    }


    public void changeNickname(String nickname){
        User user = new User(liveUser.getValue().geteMail(), nickname,liveUser.getValue().getRegDate(),liveUser.getValue().getThumbnail());
        db.collection("User").document(userid).set(user,SetOptions.merge());
=======
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
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
    }
}
