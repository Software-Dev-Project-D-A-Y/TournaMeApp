package com.example.tournameapp.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UsersService {

    private final static String USERS = "Users";
    private final static String USER_NAMES = "Usernames";
    private final static String EMAILS = "Emails";
    public final static String PLAYERS = "Players";
    public final static String MANAGERS = "Managers";

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private HashMap<String, String> userNames;
    private HashMap<String, String> emails;
    private HashMap<String, Manager> managers;
    private HashMap<String, Player> players;

    private static UsersService instance = null;

    private UsersService() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(USERS);


        userNames = new HashMap<>();
        emails = new HashMap<>();
        managers = new HashMap<>();
        players = new HashMap<>();

        dbRef.child(MANAGERS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String userName = snapshot.getKey();
                Manager manager = snapshot.getValue(Manager.class);
                managers.put(userName, manager);
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
        dbRef.child(PLAYERS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String userName = snapshot.getKey();
                Player player = snapshot.getValue(Player.class);
                players.put(userName, player);
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
        dbRef.child(USER_NAMES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String userName = snapshot.getKey();
                String type = snapshot.getValue(String.class);
                userNames.put(userName, type);
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
                String userName = snapshot.getKey();
                emails.put(userName, email);
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

    public static UsersService getInstance() {
        if (instance == null){
            instance = new UsersService();
        }
        return instance;
    }

    // INSERTIONS
    public boolean insertUser(Manager newManager) {
        dbRef.child(MANAGERS).child(newManager.getUserName()).setValue(newManager);
        dbRef.child(USER_NAMES).child(newManager.getUserName()).setValue(MANAGERS);
        dbRef.child(EMAILS).child(newManager.getUserName()).setValue(newManager.getEmail());
        return true;
    }

    public boolean insertUser(Player newPlayer) {
        dbRef.child(PLAYERS).child(newPlayer.getUserName()).setValue(newPlayer);
        dbRef.child(USER_NAMES).child(newPlayer.getUserName()).setValue(PLAYERS);
        dbRef.child(EMAILS).child(newPlayer.getUserName()).setValue(newPlayer.getEmail());
        return true;
    }

    // LOAD
    public void loadUsersData(final OnDataLoadedListener onDataLoadedListener){
        onDataLoadedListener.onStart();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onDataLoadedListener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error",error.getMessage());
            }
        });
    }
//
    // GETTERS
    public boolean isEmailExists(String email) {
        return emails.containsValue(email);
    }

    public boolean isUsernameExists(String username) {
        return userNames.containsKey(username);
    }

    public String getUserType(String userName) {
        return userNames.get(userName);
    }

    public Manager getManager(String userName) {
        return managers.get(userName);
    }

    public Player getPlayer(String userName) {
        return players.get(userName);
    }

}
