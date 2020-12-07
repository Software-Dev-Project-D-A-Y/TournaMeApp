package com.example.tournameapp.interfaces;

import android.content.SharedPreferences;

import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

public interface OnLoginListener {

    SharedPreferences getSharedPreferences();
    void onUserNameError(String message);
    void onPasswordError(String message);
    void onLogin(Manager manager);
    void onLogin(Player player);

}
