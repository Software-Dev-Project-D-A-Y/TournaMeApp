package com.example.tournameapp.utils;

import com.example.tournameapp.app.TournamentFragment;
import com.example.tournameapp.database.MatchesService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.interfaces.TournamentListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TournamentPresenter {

    private TournamentListener listener;
    private MatchesService matchesService;

    private Tournament tournament;

    public TournamentPresenter(TournamentListener listener, Tournament tournament) {
        this.listener = listener;
        this.tournament = tournament;
        matchesService = MatchesService.getInstance();
    }

    public void loadAllMatches() {
        matchesService.loadAllMatches(tournament, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<Match> matches = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Match match = child.getValue(Match.class);
                    matches.add(match);
                }

                listener.onMatchesLoad(matches);
            }
        });
    }
}
