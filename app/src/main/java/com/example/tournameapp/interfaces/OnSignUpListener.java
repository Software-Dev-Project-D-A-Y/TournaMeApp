package com.example.tournameapp.interfaces;


/**
 * Interface for listening to sign-up events
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public interface OnSignUpListener {


    /**
     * called on error in first name field
     * @param message message to show
     */
    void onFirstNameError(String message);


    /**
     * called on error in last name field
     * @param message message to show
     */
    void onLastNameError(String message);

    /**
     * called on error in age field
     * @param message message to show
     */
    void onAgeError(String message);


    /**
     * called on error in username field
     * @param message message to show
     */
    void onUsernameError(String message);

    /**
     * called on error in password field
     * @param message message to show
     */
    void onPasswordError(String message);

    /**
     * called when passwords are not matching
     * @param message message to show
     */
    void onMatchedPasswordsError(String message);

    /**
     * called on error in email field
     * @param message message to show
     */
    void onEmailError(String message);

    /**
     * called when sign-up completed successfully
     */
    void onSignUp();
}
