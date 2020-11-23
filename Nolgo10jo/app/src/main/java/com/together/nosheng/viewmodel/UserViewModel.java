package com.together.nosheng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.repository.FirebaseAuthDao;
import com.together.nosheng.repository.FirebaseAuthDaoImpl;

public class UserViewModel extends ViewModel {

    private FirebaseAuthDao dao;

//    public LiveData<User> user;
    public LiveData<FirebaseUser> firebaseUser;

    public UserViewModel() {
        dao = new FirebaseAuthDaoImpl();

        if (firebaseUser == null) {
            firebaseUser =  dao.getFirebaseUser();
        }
    }

}
