package com.example.bmop;

import android.app.Application;

import com.example.bmop.utils.AppPreferences;
import com.google.firebase.FirebaseApp;

public class GoogleLoginApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        AppPreferences.getInstance().Initialize(this);
    }
}