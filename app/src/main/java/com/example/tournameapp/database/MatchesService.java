package com.example.tournameapp.database;

import androidx.annotation.NonNull;

import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatchesService {
    private static final String MATCHES = "Matches";
    private static final String ALL_MATCHES = "All-Matches";
    private static final String MATCHES_PLAYED = "Matches-Played";

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private static MatchesService instance = null;

    private MatchesService() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(MATCHES);
    }

    public static MatchesService getInstance() {
        if (instance == null) {
            instance = new MatchesService();
        }
        return instance;
}

    // INSERTIONS
    public void addMatch(Match match) {
        String tournamentID = match.getTournament().getId();
        String matchKey = dbRef.child(ALL_MATCHES).child(tournamentID).push().getKey();
        if (matchKey == null) return;
        match.setId(matchKey);
        dbRef.child(ALL_MATCHES).child(tournamentID).child(matchKey).setValue(match);
    }

    public void updateMatch(Match match) {
        String tournamentID = match.getTournament().getId();
        String matchKey = match.getId();
        dbRef.child(ALL_MATCHES).child(tournamentID).child(matchKey).setValue(match);
        dbRef.child(MATCHES_PLAYED).child(tournamentID).child(matchKey).setValue(match);
    }

    // LOAD
    public void loadMatchesPlayed(Tournament tournament, final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(MATCHES_PLAYED).child(tournament.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void loadAllMatches(Tournament tournament, final OnDataLoadedListener listener) {
        listener.onStart();
        dbRef.child(ALL_MATCHES).child(tournament.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
