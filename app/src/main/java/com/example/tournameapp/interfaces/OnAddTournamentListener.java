package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Tournament;

public interface OnAddTournamentListener {

    void onTournamentNameError(String message);
    void onCapacityError(String message);
    void onAdd(Tournament newTournament);
}
