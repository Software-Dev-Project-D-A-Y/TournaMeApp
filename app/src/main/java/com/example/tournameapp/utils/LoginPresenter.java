package com.example.tournameapp.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnLoginListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

public class LoginPresenter {

    private OnLoginListener onLoginListener;
    private UsersService usersService;

    public LoginPresenter(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
        this.usersService = UsersService.getInstance();
    }

    public void logIn(String userName, String password, boolean rememberMe) {

        boolean isExists = usersService.isUsernameExists(userName);
        if (!isExists) {
            onLoginListener.onUserNameError("No such user!");
            return;
        }

        String type = usersService.getUserType(userName);
        switch (type) {
            case UsersService.MANAGERS:
                Manager manager = usersService.getManager(userName);

                if (!manager.getPassword().equals(password)) {
                    onLoginListener.onPasswordError("Wrong password!");
                    break;
                }
                onLoginListener.onLogin(manager);
                break;

            case UsersService.PLAYERS:
                Player player = usersService.getPlayer(userName);

                if (!player.getPassword().equals(password)) {
                    onLoginListener.onPasswordError("Wrong password!");
                    break;
                }
                onLoginListener.onLogin(player);
                break;
        }

        setLoggedUser(userName,rememberMe);
    }

    private void setLoggedUser(String userName,boolean rememberMe) {
        SharedPreferences sharedPreferences = onLoginListener.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberUser", rememberMe);
        editor.putString("loggedUser", userName);
        editor.apply();
    }

    public void checkLoggedUser() {
        usersService.displayData();

        SharedPreferences sharedPreferences = onLoginListener.getSharedPreferences();
        boolean isLogged = sharedPreferences.getBoolean("rememberUser", false);
        Log.d("isLogged", isLogged + "");
        if (!isLogged) return;

        String userLogged = sharedPreferences.getString("loggedUser", null);
        Log.d("userLogged", userLogged + "");
        if (userLogged == null) return;

        String type = usersService.getUserType(userLogged);
        Log.d("type",type);

        switch (type) {
            case UsersService.MANAGERS:
                Manager manager = usersService.getManager(userLogged);
                onLoginListener.onLogin(manager);
                break;

            case UsersService.PLAYERS:
                Player player = usersService.getPlayer(userLogged);
                onLoginListener.onLogin(player);
                break;
        }
    }
}
