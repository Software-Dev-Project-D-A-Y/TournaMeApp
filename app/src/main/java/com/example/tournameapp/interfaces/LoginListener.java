package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

public interface LoginListener {

    void onUserNameError(String message);
    void onPasswordError(String message);
    void login(Manager manager);
    void login(Player player);

}
