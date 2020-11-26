package com.together.nosheng.repository;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public interface FirebaseAuthDao {
    MutableLiveData<FirebaseUser> getFirebaseUser();
}
