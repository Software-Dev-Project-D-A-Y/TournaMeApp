package com.example.tournameapp.Utils;

public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean isManager;

    public User(){}

    public User(long id, String firstName, String lastName, String username, String password, String email, boolean isManager) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isManager = isManager;
        //
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isManager() {
        return isManager;
    }
}
