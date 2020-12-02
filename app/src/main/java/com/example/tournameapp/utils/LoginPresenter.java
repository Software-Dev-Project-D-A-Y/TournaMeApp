package com.example.tournameapp.utils;

import android.util.Log;

import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.LoginListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

public class LoginPresenter {

    private LoginListener loginListener;
    private UsersService usersService;

    public LoginPresenter(LoginListener loginListener) {
        this.loginListener = loginListener;
        usersService = UsersService.getInstance();
    }

    public void logIn(String userName, String password, boolean rememberMe) {

        boolean isExists = usersService.isUsernameExists(userName);
        if (!isExists) {
            loginListener.onUserNameError("No such user!");
            return;
        }
        String type = usersService.getUserType(userName);
        switch (type) {
            case UsersService.MANAGERS:
                Manager manager = usersService.loginManager(userName, password);
                if (manager == null) {
                    loginListener.onPasswordError("Wrong password!");
                } else {
                    loginListener.login(manager);
                }
                break;

            case UsersService.PLAYERS:
                Player player = usersService.loginPlayer(userName, password);
                if (player == null) {
                    loginListener.onPasswordError("Wrong password!");
                } else {
                    loginListener.login(player);
                }
                break;
        }
    }
}
