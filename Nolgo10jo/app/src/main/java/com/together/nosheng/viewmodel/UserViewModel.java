package com.together.nosheng.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.model.plan.Plan;
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

    private LiveData<List<Plan>> bookmarkList;
    private LiveData<List<String>> bookmarkID;

    private LiveData<List<String>> userNicknames;

    String TAG = "User ViewModel : ";

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public void settingFriend() {
        this.friendNickName = userRepository.findFriend();
    }


    public void changeNickname(String nickName) {
        userRepository.changeNickname(nickName);
    }

    public LiveData<User> getLiveUser() {
        return liveUser;
    }

    public void setLiveUser() {
        liveUser = userRepository.getLiveUser();
    }

    public LiveData<List<User>> getUserFriendList(){
        return userFriendList;
    }

    public void setUserFriendList(){
        userFriendList = userRepository.getUserFriendList();
    }

    public  LiveData<List<Plan>> getBookmarkList() {return bookmarkList;}

    public void setBookmarkList() {bookmarkList = userRepository.getBookMarkList();}

    public LiveData<List<String>> getBookmarkID() {return bookmarkID; }

    public void updateUserProjectList(String uid,List<String> projectList){
        userRepository.updateUserProjectList(uid, projectList);
    }

//    public List<String> getUserProject(){
//        return userRepository.getUserProject();
//    }

    public List<String> getMemberProject(String member){
        return userRepository.getMemberProject(member);
    }

//    public MutableLiveData<List<String>> getMemberProject(String member){
//        return userRepository.getMemberProject(member);
//    }

//    public List<String> getAllUid(){
//        return userRepository.getAllUid();
//    }

    public void setUserNickName(List<String> members){
        userNicknames = userRepository.getUserNickName(members);
    }

    public LiveData<List<String>> getUserNickNames(){
        return userNicknames;
    }

    public void updateUserBookmarList(String planId, List<String> bookmarkList) {
        userRepository.updateUserBookmarList(planId, bookmarkList);
    }
}
