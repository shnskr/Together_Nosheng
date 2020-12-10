package com.together.nosheng.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.model.user.User;
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
        return liveUser;
    }

    public void setLiveUser() {
        liveUser = userRepository.getLiveUser();
    }

}