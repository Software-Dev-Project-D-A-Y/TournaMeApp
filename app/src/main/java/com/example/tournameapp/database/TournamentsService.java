package com.example.tournameapp.database;

import android.util.Log;

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

    private static final String TOURNAMENTS = "Tournaments";
    private static final String ALL_TOURNAMENTS = "All-Tournaments";
    private static final String MANAGER_TOURNAMENTS = "Manager-Tournaments";
    private static final String TOURNAMENT_PLAYERS = "Tournament-Players";
    private static final String PLAYER_TOURNAMENTS = "Player-Tournaments";


    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private static TournamentsService instance = null;

    public static TournamentsService getInstance() {
        if (instance == null) {
            instance = new TournamentsService();
        }

        return instance;
    }

    private TournamentsService() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(TOURNAMENTS);

    }

    // INSERTIONS
    public boolean insertTournament(Tournament tournament) {
        String key = dbRef.child(ALL_TOURNAMENTS).push().getKey();
        if (key == null) return false;

        tournament.setId(key);
        dbRef.child(ALL_TOURNAMENTS).child(key).setValue(tournament);
        dbRef.child(MANAGER_TOURNAMENTS).child(tournament.getManager().getUserName()).child(key).setValue(tournament);
        return true;
    }

    public boolean updateTournament(final Tournament tournament) {
        dbRef.child(ALL_TOURNAMENTS).child(tournament.getId()).setValue(tournament);
        dbRef.child(MANAGER_TOURNAMENTS).child(tournament.getManager().getUserName()).child(tournament.getId()).setValue(tournament);
        dbRef.child(PLAYER_TOURNAMENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot player : snapshot.getChildren()) {
                    if (player.hasChild(tournament.getId())) {
                        String playerKey = player.getKey();
                        if (playerKey == null) return;

                        dbRef.child(PLAYER_TOURNAMENTS).child(playerKey).child(tournament.getId()).setValue(tournament);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return true;
    }

    public void addPlayerToTournament(Player player, Tournament tournament) {
        String username = player.getUserName();
        String tournamentID = tournament.getId();
        dbRef.child(TOURNAMENT_PLAYERS).child(tournamentID).child(username).setValue(player);
        dbRef.child(PLAYER_TOURNAMENTS).child(username).child(tournamentID).setValue(tournament);
    }

    // LOAD
    public void loadAllTournaments(final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(ALL_TOURNAMENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error);
            }
        });

    }

    public void loadTournament(String tournamentID, final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(ALL_TOURNAMENTS).child(tournamentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error);
            }
        });
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
                listener.onError(error);
            }
        });
    }

    public void loadManagerTournaments(Manager manager, final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(MANAGER_TOURNAMENTS).child(manager.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error);
            }
        });
    }

    public void loadPlayerTournaments(Player player, final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(PLAYER_TOURNAMENTS).child(player.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error);
            }
        });
    }

    // REMOVES
    public void removePlayerFromTournament(Player player,Tournament tournament) {
        String username = player.getUserName();
        String tournamentID = tournament.getId();
        dbRef.child(TOURNAMENT_PLAYERS).child(tournamentID).child(username).removeValue();
        dbRef.child(PLAYER_TOURNAMENTS).child(username).child(tournamentID).removeValue();
    }

    public void removeTournament(final Tournament tournament) {
        final String key = tournament.getId();
        // remove from all-tournaments and manager-tournaments
        dbRef.child(ALL_TOURNAMENTS).child(key).removeValue();
        dbRef.child(MANAGER_TOURNAMENTS).child(tournament.getManager().getUserName()).child(key).removeValue();
        // remove tournament from each player
        loadTournamentPlayers(tournament, new OnDataLoadedListener() {
            @Override
            public void onStart() {
                Log.d("Removing","Removing from tournament players");
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String playerID = child.getKey();
                    Log.d("playerID",playerID);
                    dbRef.child(PLAYER_TOURNAMENTS).child(playerID).child(key).removeValue();
                }
                // remove from tournament-players
                dbRef.child(TOURNAMENT_PLAYERS).child(key).removeValue();
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }
}
