package com.example.tournameapp.utils.validation;

public class Validation {

    // validate first name
    public static boolean isValidFirstName(String firstName) throws ValidationException {
        String pattern = "[A-Z][a-z]*";
        if(firstName.matches(pattern)){
            return true;
        }
        throw new ValidationException(ValidationErrorType.FIRSTNAME);
    }

    // validate last name
    public static boolean isValidLastName(String lastName) throws ValidationException {
        String pattern = "[A-Z][a-z]+([ '-][a-zA-Z]+)*";
        if(lastName.matches(pattern)){
            return true;
        }
        throw new ValidationException(ValidationErrorType.LASTNAME);
    }

    // validate User Name
    public static boolean isValidUsername(String userName) throws ValidationException {
        String pattern = "[A-Za-z0-9_]+";
        if(userName.matches(pattern)){
            return true;
        }
        throw new ValidationException(ValidationErrorType.USERNAME);
    }

    // validate age
    public static boolean isValidAge(int age) throws ValidationException {
        if (age >= 1 && age <= 120) {
            return true;
        }
        throw new ValidationException(ValidationErrorType.AGE);
    }


    // validate password

//    (?=.*[0-9]) a digit must occur at least once
//    (?=.*[a-z]) a lower case letter must occur at least once
//    (?=.*[A-Z]) an upper case letter must occur at least once
//    (?=.*[@#$%^&+=]) a special character must occur at least once
//    (?=\\S+$) no whitespace allowed in the entire string
//    .{8,} at least 8 characters

    public static boolean isValidPassword(String password) throws ValidationException {
        String pattern = "(?=\\S+$).{8,}";
        if (password.matches(pattern)) {
            return true;
        }
        throw new ValidationException(ValidationErrorType.PASSWORD);
    }

    // validate Confirm password
    public static boolean isMatchedPasswords(String password, String confirmPassword) throws ValidationException {
        if (password.equals(confirmPassword)) {
            return true;
        }
        throw new ValidationException(ValidationErrorType.PASSWORD_MATCH);
    }

    // validate email
    public static boolean isValidEmail(String email) throws ValidationException {
        String pattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(pattern)) {
            return true;
        }
        throw new ValidationException(ValidationErrorType.EMAIL);
    }
}




