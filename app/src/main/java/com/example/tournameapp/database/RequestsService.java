package com.example.tournameapp.database;

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

    public boolean insertTournamentRequest(Tournament tournament, Player player) {
        TournamentRequest request = new TournamentRequest(tournament, player, true, false);
        String key = dbRef.child("All-Requests").push().getKey();
        request.setId(key);

        dbRef.child("All-Requests").child(key).setValue(request);
        dbRef.child("Player-Requests").child(player.getUserName()).child(key).setValue(request);

        return true;
    }

    public void loadPlayerRequests(Player player, final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child("Player-Requests").child(player.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateRequest(TournamentRequest requestUpdated) {
        dbRef.child("All-Requests").child(requestUpdated.getId()).setValue(requestUpdated);
        dbRef.child("Player-Requests").child(requestUpdated.getPlayer().getUserName()).child(requestUpdated.getId()).setValue(requestUpdated);
    }

    public void removeRequest(TournamentRequest requestRemoved){
        dbRef.child("All-Requests").child(requestRemoved.getId()).removeValue();
        dbRef.child("Player-Requests").child(requestRemoved.getPlayer().getUserName()).child(requestRemoved.getId()).removeValue();
    }
}
