package com.example.tournameapp.app;

import com.example.tournameapp.database.UsersHelper;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.utils.validation.Validation;
import com.example.tournameapp.utils.validation.ValidationException;

public class SignUpPresenter {


    private SignUpListener signUpListener;
    private UsersHelper usersHelper;

    public SignUpPresenter(SignUpListener signUpListener)
    {
        this.signUpListener = signUpListener;
        usersHelper = new UsersHelper();
    }

    public void signUpManager(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword) {
        boolean isValid = checkValidation(firstName,lastName,age,email,username,password,confirmPassword);
        if(isValid){
            //DATABASE
            signUpListener.signUp();
        }
    }

    public void signUpPlayer(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword) {
        boolean isValid = checkValidation(firstName,lastName,age,email,username,password,confirmPassword);
        if(isValid){
            //DATABASE
            signUpListener.signUp();
        }
    }

    private boolean checkValidation(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword){
        boolean res = true;

        try {
            Validation.isValidFirstName(firstName);
        } catch (ValidationException e) {
            signUpListener.onFirstNameError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidLastName(lastName);
        } catch (ValidationException e) {
            signUpListener.onLastNameError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidAge(age);
        } catch (ValidationException e) {
            signUpListener.onAgeError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidUsername(username);
        } catch (ValidationException e) {
            signUpListener.onUsernameError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidPassword(password);
        } catch (ValidationException e) {
            signUpListener.onPasswordError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isMatchedPasswords(password,confirmPassword);
        } catch (ValidationException e) {
            signUpListener.onMatchedPasswordsError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidEmail(email);
        } catch (ValidationException e) {
            signUpListener.onEmailError(e.getValidationError());
            res = false;
        }

        return res;
    }
}
