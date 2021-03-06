package com.example.tournameapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestsService {

    private static final String REQUESTS = "Requests";
    private static final String ALL_REQUESTS = "All-Requests";
    private static final String MANAGER_REQUESTS = "Manager-Requests";
    private static final String PLAYER_REQUESTS = "Player-Requests";

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private static RequestsService instance = null;

    private RequestsService() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(REQUESTS);
    }

    public static RequestsService getInstance() {
        if (instance == null) {
            instance = new RequestsService();
        }
        return instance;
    }

    // INSERTIONS
    public boolean insertTournamentRequest(Tournament tournament, Player player, boolean fromManager) {


        TournamentRequest request = new TournamentRequest(tournament, player, fromManager, !fromManager);
        String key = dbRef.child(ALL_REQUESTS).push().getKey();
        if(key == null) return false;

        request.setId(key);

        dbRef.child(ALL_REQUESTS).child(key).setValue(request);
        if (!fromManager) {
            dbRef.child(MANAGER_REQUESTS).child(tournament.getId()).child(key).setValue(request);
            return true;
        }
        dbRef.child(PLAYER_REQUESTS).child(player.getUserName()).child(key).setValue(request);
        return true;
    }

    public void updateRequest(TournamentRequest requestUpdated) {
        dbRef.child(ALL_REQUESTS).child(requestUpdated.getId()).setValue(requestUpdated);
        dbRef.child(PLAYER_REQUESTS).child(requestUpdated.getPlayer().getUserName()).child(requestUpdated.getId()).setValue(requestUpdated);
    }

    // REMOVES
    public void removeRequest(TournamentRequest requestRemoved) {
        dbRef.child(ALL_REQUESTS).child(requestRemoved.getId()).removeValue();
        dbRef.child(PLAYER_REQUESTS).child(requestRemoved.getPlayer().getUserName()).child(requestRemoved.getId()).removeValue();
    }

    public void removeTournamentRequests(final Tournament tournament) {
        loadAllRequests(new OnDataLoadedListener() {
            @Override
            public void onStart() {
                Log.d("Removing request","Removing requests from "+tournament.getTournamentName());
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for ( DataSnapshot child : dataSnapshot.getChildren()) {
                    TournamentRequest req = child.getValue(TournamentRequest.class);
                    if(req.getTournament().equals(tournament)) {
                        dbRef.child(ALL_REQUESTS).child(req.getId()).removeValue();
                        dbRef.child(PLAYER_REQUESTS).child(req.getPlayer().getUserName()).child(req.getId()).removeValue();
                    }
                }
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    // LOAD
    public void loadPlayerRequests(Player player, final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(PLAYER_REQUESTS).child(player.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void loadAllRequests(final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(ALL_REQUESTS).addListenerForSingleValueEvent(new ValueEventListener() {
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
}
