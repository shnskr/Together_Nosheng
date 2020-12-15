package com.together.nosheng.viewmodel;

<<<<<<< HEAD
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
=======
import android.util.Log;

>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.model.user.User;
<<<<<<< HEAD
import com.together.nosheng.repository.FirebaseAuthDao;
import com.together.nosheng.repository.FirebaseAuthDaoImpl;
import com.together.nosheng.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private FirebaseAuthDao dao;
    public LiveData<User> liveUser;
    public LiveData<FirebaseUser> firebaseUser;
    private UserRepository userRepository = new UserRepository();

    public UserViewModel() {
        setUserId(firebaseUser.getValue().getUid());
        dao = new FirebaseAuthDaoImpl();
        getFirebaseUser();
        FindUserInfo();

    }

    public void setUserId(String userId) {
        userRepository.setUserId(userId);
    }

    public void changeNickname(String value) {
        userRepository.changeNickname(value);
    }



    public void FindUserInfo () {

        this.liveUser = userRepository.findAll();
    }

    public LiveData<User> userModelLiveData() {
=======
import com.together.nosheng.repository.UserRepository;
import com.together.nosheng.util.GlobalApplication;

import java.util.ArrayList;

public class UserViewModel extends ViewModel {
    private LiveData<User> liveUser;
    private UserRepository userRepository;
    private LiveData<ArrayList<String>> friendNickName;
    private static String Userid;

    String TAG = "User ViewModel : ";

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public void settingFriend() {
        this.friendNickName = userRepository.findFriend();
    }


    public void changeNickname(String value) {
        User user = liveUser.getValue();
        user.setNickName(value);
        userRepository.changeNickname(user);
    }


    public LiveData<ArrayList<String>> friendLiveData() {
        return friendNickName;
    }

    public void setFriend(ArrayList<String> lists) {
        userRepository.setFriend(lists);
    }

    public LiveData<ArrayList<String>> friendNameLiveData() {
        return friendNickName;
    }

    public LiveData<User> getLiveUser() {
>>>>>>> 9e9310ffcb3c03b6acde7cdc93f70eb13219809c
        return liveUser;
    }

    public void setLiveUser() {
        liveUser = userRepository.getLiveUser();
    }

}