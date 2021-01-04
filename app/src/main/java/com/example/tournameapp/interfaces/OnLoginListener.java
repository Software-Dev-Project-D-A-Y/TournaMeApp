package com.example.tournameapp.interfaces;

import android.content.SharedPreferences;

import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

/**
 *  Interface for listening to login events
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public interface OnLoginListener {

    /**
     *
     * @return  shared preferences object
     */
    SharedPreferences getSharedPreferences();

    /**
     * called when error on username field is occurred
     * @param message   message to show
     */
    void onUserNameError(String message);

    /**
     * called when error on password field is occurred
     * @param message   message to show
     */
    void onPasswordError(String message);

    /**
     * called when successfully logged with manager
     * @param manager   manager that logs in
     */
    void onLogin(Manager manager);

    /**
     * called when successfully logged with player
     * @param player    player that logs in
     */
    void onLogin(Player player);

}
