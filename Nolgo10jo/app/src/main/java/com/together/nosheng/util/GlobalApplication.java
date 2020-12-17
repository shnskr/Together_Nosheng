package com.together.nosheng.util;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.view.LoginActivity;

public class GlobalApplication extends Application {

    public static FirebaseUser firebaseUser;

    @Override
    public void onCreate() {
        super.onCreate();
        Places.initialize(getApplicationContext(), "AIzaSyDTtW-jwQI9kLyu0kt6AXOJmLxPlHrBDPE");
        setFirebaseUser();
    }

    public static void setFirebaseUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
        setFirebaseUser();
    }
}
