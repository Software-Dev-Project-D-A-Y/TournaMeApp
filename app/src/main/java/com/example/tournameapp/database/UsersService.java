package com.example.tournameapp.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UsersService {

    private final static String USERS = "Users";
    private final static String USER_NAMES = "Usernames";
    private final static String EMAILS = "Emails";
    private final static String PLAYERS = "Players";
    private final static String MANAGERS = "Managers";

    private FirebaseDatabase database;
    private DatabaseReference dbRef;


    private ArrayList<String> userNames;
    private ArrayList<String> emails;

    public UsersService() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(USERS);

        userNames = new ArrayList<>();
        emails = new ArrayList<>();
        dbRef.child(USER_NAMES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String userName = snapshot.getValue(String.class);
                Log.d("USER_NAMES.onChildAdded",userName);
                userNames.add(userName);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dbRef.child(EMAILS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email = snapshot.getValue(String.class);
                Log.d("EMAILS.onChildAdded",email);
                emails.add(email);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean insertUser(Manager newManager){
        Log.d("Insert user","newManager");
        dbRef.child(MANAGERS).child(newManager.getUserName()).setValue(newManager);
        dbRef.child(USER_NAMES).push().setValue(newManager.getUserName());
        dbRef.child(EMAILS).push().setValue(newManager.getEmail());
        return true;
    }

    public boolean insertUser(Player newPlayer){
        Log.d("Insert user","newPlayer");
        dbRef.child(PLAYERS).child(newPlayer.getUserName()).setValue(newPlayer);
        dbRef.child(USER_NAMES).push().setValue(newPlayer.getUserName());
        dbRef.child(EMAILS).push().setValue(newPlayer.getEmail());
        return true;
    }

    public boolean isEmailExists(String email){
        return emails.contains(email);
    }
    public boolean isUsernameExists(String username){
        Log.d("userNames",userNames.toString());
        return userNames.contains(username);
    }
}
