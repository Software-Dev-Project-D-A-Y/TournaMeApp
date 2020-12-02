package com.example.tournameapp.utils;

import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.LoginListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

public class LoginPresenter {

   private LoginListener loginListener;
    private UsersService usersService;

    public LoginPresenter(LoginListener loginListener) {
        this.loginListener = loginListener;
        usersService = new UsersService();
    }
    public void logIn(String userName, String password, boolean rememberMe){

        boolean isExists = usersService.isUsernameExists(userName);
        if(!isExists){
            //validation
           // boolean isCorrect = usersService.isPasswordMatch(userName,password);
        }
        String type = usersService.getUserType(userName);
        switch (type)
        {
            case  UsersService.MANAGERS:
               Manager manager = usersService.getManager(userName,password);
                break;

            case  UsersService.PLAYERS:
                Player player = usersService.getPlayer(userName,password);

                break;
        }
    }
}
