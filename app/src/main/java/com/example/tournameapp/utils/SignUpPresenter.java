package com.example.tournameapp.utils;

import com.example.tournameapp.interfaces.OnSignUpListener;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.utils.validation.Validation;
import com.example.tournameapp.utils.validation.ValidationException;

public class SignUpPresenter {


    private OnSignUpListener onSignUpListener;
    private UsersService usersService;

    public SignUpPresenter(OnSignUpListener onSignUpListener) {
        this.onSignUpListener = onSignUpListener;
        this.usersService = UsersService.getInstance();
    }

    public void signUpManager(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword) {
        boolean isValid = checkValidation(firstName, lastName, age, email, username, password, confirmPassword);
        if (!isValid) return;

        Manager newManager = new Manager(firstName, lastName, age, email, username, password);
        boolean isInserted = usersService.insertUser(newManager);

        if (isInserted)
            onSignUpListener.onSignUp();

    }

    public void signUpPlayer(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword) {
        boolean isValid = checkValidation(firstName, lastName, age, email, username, password, confirmPassword);
        if (!isValid) return;

        Player newPlayer = new Player(firstName, lastName, age, email, username, password);
        usersService.insertUser(newPlayer);
        boolean isInserted = usersService.insertUser(newPlayer);

        if (isInserted)
            onSignUpListener.onSignUp();

    }

    private boolean checkValidation(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword) {
        boolean res = true;

        try {
            Validation.isValidFirstName(firstName);
        } catch (ValidationException e) {
            onSignUpListener.onFirstNameError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidLastName(lastName);
        } catch (ValidationException e) {
            onSignUpListener.onLastNameError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidAge(age);
        } catch (ValidationException e) {
            onSignUpListener.onAgeError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidUsername(username);
        } catch (ValidationException e) {
            onSignUpListener.onUsernameError(e.getValidationError());
            res = false;
        }

        if (usersService.isUsernameExists(username)) {
            onSignUpListener.onUsernameError("Username is already Exists!");
            res = false;
        }

        try {
            Validation.isValidPassword(password);
        } catch (ValidationException e) {
            onSignUpListener.onPasswordError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isMatchedPasswords(password, confirmPassword);
        } catch (ValidationException e) {
            onSignUpListener.onMatchedPasswordsError(e.getValidationError());
            res = false;
        }

        try {
            Validation.isValidEmail(email);
        } catch (ValidationException e) {
            onSignUpListener.onEmailError(e.getValidationError());
            res = false;
        }

        if (usersService.isEmailExists(email)) {
            onSignUpListener.onEmailError("Email is already Exists!");
            res = false;
        }

        return res;
    }
}
