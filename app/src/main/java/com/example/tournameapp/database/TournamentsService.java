package com.example.tournameapp.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TournamentsService {

    public static final String TOURNAMENTS = "Tournaments";

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private Manager manager;

    private static TournamentsService instance = null;

    public static TournamentsService getInstance(){
        if(instance == null){
            instance = new TournamentsService();
        }

        return instance;
    }

    private TournamentsService(){
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(TOURNAMENTS);

    }

    public boolean insertTournament(Tournament tournament){
        String key = dbRef.child("tournaments").push().getKey();

        dbRef.child("tournaments").child(key).setValue(tournament);
        dbRef.child("manager-tournaments").child(tournament.getManager().getUserName()).child(key).setValue(tournament);
        return true;
    }

    public void loadManagerTournaments(Manager manager, final OnDataLoadedListener listener){
        listener.onStart();
        dbRef.child("manager-tournaments").child(manager.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
