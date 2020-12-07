package com.example.tournameapp.utils;

import com.example.tournameapp.interfaces.OnAddTournamentListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.utils.validation.Validation;

public class AddTournamentPresenter {

    private Manager manager;
    private OnAddTournamentListener listener;

    public AddTournamentPresenter(Manager manager, OnAddTournamentListener listener){
        this.manager = manager;
        this.listener = listener;
    }


    public void addTournament(String tourName, String tourDesc, int tourCap) {
        boolean isValid = checkValidation(tourName,tourDesc,tourCap);
        if(!isValid) return;

        Tournament newTournament = new Tournament(manager,tourName,tourDesc,tourCap);
        listener.add(newTournament);

    }

    private boolean checkValidation(String tourName, String tourDesc, int tourCap) {
        boolean res = true;

        // VALIDATION
        if(tourCap == -1){
            listener.onCapacityError("Wrong number!");
            res = false;
        }

        return res;
    }
}
