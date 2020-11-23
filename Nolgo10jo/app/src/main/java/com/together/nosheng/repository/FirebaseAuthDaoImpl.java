package com.together.nosheng.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class FirebaseAuthDaoImpl implements FirebaseAuthDao{
    private FirebaseAuth auth;

    public FirebaseAuthDaoImpl() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public MutableLiveData<FirebaseUser> getFirebaseUser() {
        MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
        user.setValue(auth.getCurrentUser());
        return user;
    }
}
