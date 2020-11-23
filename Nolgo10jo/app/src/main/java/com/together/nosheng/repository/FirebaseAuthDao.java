package com.together.nosheng.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public interface FirebaseAuthDao {
    MutableLiveData<FirebaseUser> getFirebaseUser();
}
