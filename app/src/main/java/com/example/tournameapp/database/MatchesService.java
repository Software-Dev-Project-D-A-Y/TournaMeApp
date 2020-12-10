package com.example.tournameapp.database;

import com.example.tournameapp.model.Match;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchesService {
    private static final String MATCHES = "Matches";

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

    public void addMatches(Match match) {
        String tournamentID = match.getTournament().getId();
        String matchKey = dbRef.child(tournamentID).push().getKey();
        dbRef.child(tournamentID).child(matchKey).setValue(match);
    }
}
