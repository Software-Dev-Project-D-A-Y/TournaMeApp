package com.example.tournameapp.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.LoginListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.google.firebase.database.DataSnapshot;

public class LoginPresenter {

    private LoginListener loginListener;
    private UsersService usersService;

    public LoginPresenter(LoginListener loginListener) {
        this.loginListener = loginListener;
        this.usersService = UsersService.getInstance();
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
                Manager manager = usersService.getManager(userName);

                if (!manager.getPassword().equals(password)) {
                    loginListener.onPasswordError("Wrong password!");
                    break;
                }
                loginListener.login(manager);
                break;

            case UsersService.PLAYERS:
                Player player = usersService.getPlayer(userName);

                if (!player.getPassword().equals(password)) {
                    loginListener.onPasswordError("Wrong password!");
                    break;
                }
                loginListener.login(player);
                break;
        }

        setLoggedUser(userName,rememberMe);
    }

    private void setLoggedUser(String userName,boolean rememberMe) {
        SharedPreferences sharedPreferences = loginListener.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberUser", rememberMe);
        editor.putString("loggedUser", userName);
        editor.apply();
    }

    public void checkLoggedUser() {
        SharedPreferences sharedPreferences = loginListener.getSharedPreferences();
        boolean isLogged = sharedPreferences.getBoolean("rememberUser", false);
        Log.d("isLogged", isLogged + "");
        if (!isLogged) return;

        final String userLogged = sharedPreferences.getString("loggedUser", null);
        Log.d("userLogged", userLogged + "");
        if (userLogged == null) return;


        usersService.getUserType(userLogged, new UsersService.OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String type = dataSnapshot.getValue(String.class);
                checkLoggedUser(type, userLogged);
            }
        });
    }

    private void checkLoggedUser(final String type, final String userLogged) {
        usersService.getUserLogged(type, userLogged, new UsersService.OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                switch (type) {
                    case UsersService.MANAGERS:
                        Manager manager = dataSnapshot.getValue(Manager.class);
                        loginListener.login(manager);
                        break;
                    case UsersService.PLAYERS:
                        Player player = dataSnapshot.getValue(Player.class);
                        loginListener.login(player);
                        break;
                }
            }
        });
    }
}
