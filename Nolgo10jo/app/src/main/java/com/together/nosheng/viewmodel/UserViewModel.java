package com.together.nosheng.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.model.user.User;
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
        return liveUser;
    }

    public void getFirebaseUser() {
        firebaseUser = dao.getFirebaseUser();
    }

}
