package com.example.tournameapp.presenters;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnLoginListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

/**
 * Presenter class for retrieve data from database and handling login logic
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public class LoginPresenter {

    // listener to notify LoginActivity on changes
    private OnLoginListener onLoginListener;

    // for getting users data from database
    private UsersService usersService;

    /**
     * @param onLoginListener listener to notify activity on changes
     */
    public LoginPresenter(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
        // getting UsersService instance
        this.usersService = UsersService.getInstance();
    }

    /**
     * Method checks if user is already logged using SharedPreferences, if there is such user,
     * call the proper onLogin method from listener
     */
    public void checkLoggedUser() {
        SharedPreferences sharedPreferences = onLoginListener.getSharedPreferences();
        boolean isLogged = sharedPreferences.getBoolean("rememberUser", false);
        if (!isLogged) return;

        String userLogged = sharedPreferences.getString("loggedUser", null);
        if (userLogged == null) return;

        String type = usersService.getUserType(userLogged);

        if(type == null) return;

        switch (type) {
            case UsersService.MANAGERS:
                Manager manager = usersService.getManager(userLogged);
                onLoginListener.onLogin(manager);
                break;

            case UsersService.PLAYERS:
                Player player = usersService.getPlayer(userLogged);
                onLoginListener.onLogin(player);
                break;

            default:
                throw new RuntimeException("An Error Occurred");
        }
    }

    /**
     * Checks input parameters, if valid - calls the suitable onLogin method from listener,
     * otherwise - calls the suitable onError method
     *
     * If remember-me checkbox is checked, save the username on SharedPreferences
     *
     * @param userName given username
     * @param password given password
     * @param rememberMe true if rememberMe checkbox is checked
     */
    public void logIn(String userName, String password, boolean rememberMe) {

        // check if userName is exists
        boolean isExists = usersService.isUsernameExists(userName);
        if (!isExists) {
            onLoginListener.onUserNameError("No such user!");
            return;
        }

        // get user type
        String type = usersService.getUserType(userName);

        switch (type) {
            case UsersService.MANAGERS:
                Manager manager = usersService.getManager(userName);

                // checks password matching
                if (!manager.getPassword().equals(password)) {
                    onLoginListener.onPasswordError("Wrong password!");
                    return;
                }
                onLoginListener.onLogin(manager);
                break;

            case UsersService.PLAYERS:
                Player player = usersService.getPlayer(userName);

                // checks password matching
                if (!player.getPassword().equals(password)) {
                    onLoginListener.onPasswordError("Wrong password!");
                    return;
                }
                onLoginListener.onLogin(player);
                break;

            default:
                throw new RuntimeException("An Error Occurred");
        }

        // write on SharedPreferences
        setLoggedUser(userName,rememberMe);
    }

    /**
     * Save given data on SharedPreferences
     *
     * @param userName userName that logs
     * @param rememberMe whether remember-me checkbox was checked
     */
    private void setLoggedUser(String userName,boolean rememberMe) {
        SharedPreferences sharedPreferences = onLoginListener.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberUser", rememberMe);
        editor.putString("loggedUser", userName);
        editor.apply();
    }
}
