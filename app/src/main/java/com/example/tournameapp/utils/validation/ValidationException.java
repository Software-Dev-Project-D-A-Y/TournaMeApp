package com.example.tournameapp.utils.validation;

import com.example.tournameapp.utils.validation.ValidationErrorType;

public class ValidationException extends Exception {

    private final static String DEFAULT_MESSAGE = "An error occurred";
    private String validationError;

    public ValidationException(){
        super(DEFAULT_MESSAGE);
    }

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(ValidationErrorType type)
    {
        super(DEFAULT_MESSAGE);
        switch (type){
            case FIRSTNAME:
                validationError = "Error on First Name field";
                break;
            case LASTNAME:
                validationError = "Error on Last Name field";
                break;
            case USERNAME:
                validationError = "Error on Username field";
                break;
            case AGE:
                validationError = "Error on Age field";
                break;
            case PASSWORD:
                validationError = "Error on Password field";
                break;
            case PASSWORD_MATCH:
                validationError = "Password doesn't match";
                break;
            case EMAIL:
                validationError = "Error on Email field";
                break;
            default:
                validationError = DEFAULT_MESSAGE;
                break;
        }
    }

    public String getValidationError(){
        return validationError;
    }
}
