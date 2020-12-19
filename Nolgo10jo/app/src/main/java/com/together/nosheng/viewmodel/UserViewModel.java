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
import java.util.List;
import java.util.Map;

public class UserViewModel extends ViewModel {
    private LiveData<User> liveUser;
    private UserRepository userRepository;

    //친구 정보 관련
    private LiveData<ArrayList<String>> friendNickName;

    private LiveData<List<String>> friendNickNamelist;
    private LiveData<List<User>> userFriendList;

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

    public LiveData<User> getLiveUser() {
        return liveUser;
    }

    public void setLiveUser() {
        liveUser = userRepository.getLiveUser();
    }

    //정민님 코드
//    public LiveData<ArrayList<String>> friendLiveData() {
//        return friendNickName;
//    }
//    public void setFriend(ArrayList<String> lists) {
//        userRepository.setFriend(lists);
//    }
//    public LiveData<ArrayList<String>> friendNameLiveData() {
//        return friendNickName;
//    }


    public LiveData<List<String>> getFriendNickName() {
        friendNickNamelist =userRepository.getFriendList();
        return friendNickNamelist;
    }

    public LiveData<List<User>> getUserFriendList(){
        return userFriendList;
    }

    public void setUserFriendList(){
        userFriendList = userRepository.getUserFriendList();
    }

}