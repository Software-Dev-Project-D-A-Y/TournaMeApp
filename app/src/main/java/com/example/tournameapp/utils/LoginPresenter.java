package com.example.tournameapp.utils;

import com.example.tournameapp.interfaces.LoginListener;

public class LoginPresenter {

   private LoginListener loginListener;

    public LoginPresenter(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
    public void logIn(String userName, String password, boolean rememberMe){

    }
}
