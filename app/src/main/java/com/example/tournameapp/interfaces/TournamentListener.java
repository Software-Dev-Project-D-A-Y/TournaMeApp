package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public interface TournamentListener {

    void onMatchesLoaded(List<Match> matches);
    void onMatchesPlayedLoaded(List<Match> matchesPlayed);
}
