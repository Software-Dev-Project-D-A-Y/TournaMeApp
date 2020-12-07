package com.example.tournameapp.utils;

import android.util.Log;

import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.interfaces.OnAddTournamentListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;

public class AddTournamentPresenter {

    private Manager manager;
    private OnAddTournamentListener listener;
    private TournamentsService tournamentsService;


    public AddTournamentPresenter(Manager manager, OnAddTournamentListener listener) {
        this.manager = manager;
        this.listener = listener;
        this.tournamentsService = TournamentsService.getInstance();
    }


    public void addTournament(String tourName, String tourDesc, int tourCap) {
        boolean isValid = checkValidation(tourName, tourDesc, tourCap);
        if (!isValid) return;

        Tournament newTournament = new Tournament(manager, tourName, tourDesc, tourCap);

        boolean isInserted = tournamentsService.insertTournament(newTournament);
        if (isInserted) {
            listener.onAdd(newTournament);
        }
    }

    private boolean checkValidation(String tourName, String tourDesc, int tourCap) {
        boolean res = true;

        // VALIDATION
        if (tourCap == -1) {
            listener.onCapacityError("Wrong number!");
            res = false;
        }

        return res;
    }
}
