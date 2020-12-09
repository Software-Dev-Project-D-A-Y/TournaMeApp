package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public interface ManagerObserver {

    void onAddTournamentSuccess(Manager manager);
    void onMyTournamentsSuccess(List<Tournament> tournaments);
}
