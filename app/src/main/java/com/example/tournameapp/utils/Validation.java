package com.example.tournameapp.utils;

public class Validation {

    // validate first name
    public static boolean firstNameValidation( String firstName ) {
        return firstName.matches( "[A-Z][a-z]*" );
    }

    // validate last name
    public static boolean lastNameValidation( String lastName ) {
        return lastName.matches( "[A-Z]+([ '-][a-zA-Z]+)*" );
    }

    // validate User Name
    public static boolean UserNameValidation( String userName ) {
        return userName.matches("[A-Za-z0-9_]+");
    }

    // validate age
    static boolean ageValidation(int age){
        if(age>=1 && age<=120){
            return true;
        }
        else{
            //throw exception
            return false;
        }
    }


    // validate password

//    (?=.*[0-9]) a digit must occur at least once
//    (?=.*[a-z]) a lower case letter must occur at least once
//    (?=.*[A-Z]) an upper case letter must occur at least once
//    (?=.*[@#$%^&+=]) a special character must occur at least once
//    (?=\\S+$) no whitespace allowed in the entire string
//    .{8,} at least 8 characters

    static boolean passwordIsValid(String password) throws ValidationException {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (password.matches(pattern)) {
            return true;
        } else {
            // throw validation exception
            return false;
        }

    }

    // validate Confirm password
    static boolean matchPasswordAndConfirmPassword(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }
        else{
            //throw exception
            return false;
        }
    }

    // validate email
    static boolean emailIsValid(String mail) {
        String email = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(email);
    }
}




