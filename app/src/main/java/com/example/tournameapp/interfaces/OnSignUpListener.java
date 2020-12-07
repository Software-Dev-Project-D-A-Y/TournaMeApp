package com.example.tournameapp.interfaces;

public interface OnSignUpListener {

    void onFirstNameError(String message);
    void onLastNameError(String message);
    void onAgeError(String message);
    void onUsernameError(String message);
    void onPasswordError(String message);
    void onMatchedPasswordsError(String message);
    void onEmailError(String message);
    void onSignUp();
}
