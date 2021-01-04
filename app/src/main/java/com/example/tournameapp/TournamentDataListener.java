package com.example.tournameapp;

import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public interface TournamentDataListener {
    void onDataLoaded(Tournament tournament, List<Match> allMatches, List<Match> myMatches);
}
