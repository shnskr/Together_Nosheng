package com.together.nosheng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.model.user.User;
import com.together.nosheng.repository.FirebaseAuthDao;
import com.together.nosheng.repository.FirebaseAuthDaoImpl;
import com.together.nosheng.repository.UserRepository;

import java.util.ArrayList;

public class UserViewModel extends ViewModel {
    private FirebaseAuthDao dao;
    private  LiveData<User> liveUser;
    public   LiveData<FirebaseUser> firebaseUser;
    private  UserRepository userRepository = new UserRepository();
    private  LiveData<ArrayList<String>> friendNickName;
    private static String Userid;

    String TAG = "User ViewModel : ";
    public UserViewModel() {
        dao = new FirebaseAuthDaoImpl();
        getFirebaseUser();
        Userid = firebaseUser.getValue().getUid();
        userRepository = new UserRepository(Userid);
        this.liveUser = userRepository.findAll();
    }

    public UserViewModel(boolean firstLogin) {


        if (!firstLogin){
            dao = new FirebaseAuthDaoImpl();
            getFirebaseUser();
            userRepository = new UserRepository(firebaseUser.getValue().getUid());

//            Log.w(TAG , "값확인 !!!!!!!!!!!!!!!!!!!!"+ liveUser.getValue());
//            Log.w(TAG , "값확인 !!!!!!!!!!!!!!!!!!!!"+ liveUser.getValue().getFriendList());
//            this.friendNickName = userRepository.setFriend(liveUser.getValue().getFriendList());
        }
    }


    public void settingFriend() {
        this.friendNickName = userRepository.findFriend();
    }



    public void changeNickname(String value) {

        userRepository.changeNickname(value, firebaseUser.getValue().getUid());
    }



    public LiveData<User> userModelLiveData() {
        return liveUser;
    }

    public  LiveData<ArrayList<String>> friendLiveData() {
        return friendNickName;
    }



    public void getFirebaseUser() {
        firebaseUser = dao.getFirebaseUser();
    }


    public void setFriend (ArrayList<String> lists) {
        userRepository.setFriend(lists);
    }

    public LiveData<ArrayList<String>> friendNameLiveData() {
        return friendNickName;
    }

}