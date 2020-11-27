package com.together.nosheng.util;

import android.app.Application;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Places.initialize(getApplicationContext(), "AIzaSyDTtW-jwQI9kLyu0kt6AXOJmLxPlHrBDPE");
        PlacesClient placesClient = Places.createClient(this);
    }
}
