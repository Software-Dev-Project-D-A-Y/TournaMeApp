package com.example.tournameapp.presenters;

import com.example.tournameapp.interfaces.OnSignUpListener;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.utils.validation.Validation;
import com.example.tournameapp.utils.validation.ValidationException;

/**
 * Presenter class for retrieve data from database and handling sign-up logic
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public class SignUpPresenter {

    // listener to notify SignUpActivity on changes
    private OnSignUpListener onSignUpListener;

    // for getting users data from database
    private UsersService usersService;

    /**
     * @param onSignUpListener listener to notify activity on changes
     */
    public SignUpPresenter(OnSignUpListener onSignUpListener) {
        this.onSignUpListener = onSignUpListener;
        // getting UsersService instance
        this.usersService = UsersService.getInstance();
    }


    /**
     * Signing up a new manager with given parameters
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param email
     * @param username
     * @param password
     * @param confirmPassword
     */
    public void signUpManager(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword) {
        boolean isValid = checkValidation(firstName, lastName, age, email, username, password, confirmPassword);
        if (!isValid) return;

        Manager newManager = new Manager(firstName, lastName, age, email, username, password);
        boolean isInserted = usersService.insertUser(newManager);

        if (isInserted)
            onSignUpListener.onSignUp();

    }

    /**
     * Signing up a new player with given parameters
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param email
     * @param username
     * @param password
     * @param confirmPassword
     */
    public void signUpPlayer(String firstName, String lastName, int age, String email, String username, String password, String confirmPassword) {
        boolean isValid = checkValidation(firstName, lastName, age, email, username, password, confirmPassword);
        if (!isValid) return;

        Player newPlayer = new Player(firstName, lastName, age, email, username, password);
        usersService.insertUser(newPlayer);
        boolean isInserted = usersService.insertUser(newPlayer);

        if (isInserted)
            onSignUpListener.onSignUp();

    }

    /**
     * Checks if all input parameters are valid, using {@link Validation} class
     * calls the suitable error methods of listener if needed
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param email
     * @param username
     * @param password
     * @param confirmPassword
     * @return true only if all parameters are valid, false otherwise
     */
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
