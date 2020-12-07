package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Tournament;

public interface OnAddTournamentListener {

    void onCapacityError(String message);
    void add(Tournament newTournament);
}
