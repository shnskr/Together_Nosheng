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
    private UserRepository userRepository;

    public UserViewModel() {
        dao = new FirebaseAuthDaoImpl();
        getFirebaseUser();
        userRepository = new UserRepository(firebaseUser.getValue().getUid());
        this.liveUser = userRepository.findAll();
    }

    public UserViewModel(boolean firstLogin) {
        if (!firstLogin){

        }
    }




    public void changeNickname(String value) {
        userRepository.changeNickname(value);
    }



    public LiveData<User> userModelLiveData() {
        return liveUser;
    }

    public void getFirebaseUser() {
        firebaseUser = dao.getFirebaseUser();
    }

}
