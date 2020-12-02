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
<<<<<<< HEAD
    private UserRepository userRepository = new UserRepository();
=======
    private UserRepository userRepository;
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d

    public UserViewModel() {
        setUserId(firebaseUser.getValue().getUid());
        dao = new FirebaseAuthDaoImpl();
        getFirebaseUser();
<<<<<<< HEAD
        FindUserInfo();

    }

    public void setUserId(String userId) {
        userRepository.setUserId(userId);
    }

    public void changeNickname(String value) {
        userRepository.changeNickname(value);
=======
        userRepository = new UserRepository(firebaseUser.getValue().getUid());
        this.liveUser = userRepository.findAll();
    }

    public UserViewModel(boolean firstLogin) {
        if (!firstLogin){

        }
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
    }



<<<<<<< HEAD
    public void FindUserInfo () {

        this.liveUser = userRepository.findAll();
    }

=======

    public void changeNickname(String value) {
        userRepository.changeNickname(value);
    }



>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
    public LiveData<User> userModelLiveData() {
        return liveUser;
    }

    public void getFirebaseUser() {
        firebaseUser = dao.getFirebaseUser();
    }

}
