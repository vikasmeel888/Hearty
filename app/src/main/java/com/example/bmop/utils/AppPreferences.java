package com.example.bmop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bmop.models.User;
import com.google.gson.Gson;


public class AppPreferences {

    private static AppPreferences appPreferences;
    private SharedPreferences sharedPreferences;
    private static final String KEY_LOGIN = "KEY_LOGIN";
    private static final String KEY_USER = "KEY_USER";


    public AppPreferences() {
    }

    public static AppPreferences getInstance() {
        if (appPreferences == null) {
            appPreferences = new AppPreferences();
        }
        return appPreferences;
    }

    public void Initialize(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogin() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(KEY_LOGIN, false);
    }

    public void onLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_LOGIN);
        editor.remove(KEY_USER);
        editor.commit();
    }

    public void saveUser(User user) {
        Gson gson = new Gson();
        String userObject = gson.toJson(user);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_USER, userObject);
        edit.commit();
    }

    public User getUser() {
        String json = sharedPreferences.getString(KEY_USER, "");
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }
}

