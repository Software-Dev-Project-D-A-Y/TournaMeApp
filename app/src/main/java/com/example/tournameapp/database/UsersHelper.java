package com.example.tournameapp.database;

import com.example.tournameapp.model.Player;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsersHelper {

    private final static String USERS = "Users";

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    public UsersHelper() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(USERS);
    }

    public boolean insertUser(Player newUser)
    {
        ////
        return false;
    }
}
