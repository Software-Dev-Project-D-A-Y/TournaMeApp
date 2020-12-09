package com.example.tournameapp.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
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
    public static final String ALL_TOURNAMENTS = "All-Tournaments";
    public static final String MANAGER_TOURNAMENTS = "Manager-Tournaments";
    public static final String TOURNAMENT_PLAYERS = "Tournament-Players";

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
        String key = dbRef.child(ALL_TOURNAMENTS).push().getKey();
        tournament.setId(key);
        dbRef.child(ALL_TOURNAMENTS).child(key).setValue(tournament);
        dbRef.child(MANAGER_TOURNAMENTS).child(tournament.getManager().getUserName()).child(key).setValue(tournament);
        return true;
    }

    public void loadTournament(String tournamentID, final OnDataLoadedListener listener){
        listener.onStart();
        dbRef.child(ALL_TOURNAMENTS).child(tournamentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadManagerTournaments(Manager manager, final OnDataLoadedListener listener){
        listener.onStart();
        dbRef.child(MANAGER_TOURNAMENTS).child(manager.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addPlayerToTournament(Player player, Tournament tournament) {
        String username = player.getUserName();
        String tournamentID = tournament.getId();

        dbRef.child(TOURNAMENT_PLAYERS).child(tournamentID).child(username).setValue(player);
    }

    public void loadTournamentPlayers(Tournament tournament, final OnDataLoadedListener listener) {
        listener.onStart();

        dbRef.child(TOURNAMENT_PLAYERS).child(tournament.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
