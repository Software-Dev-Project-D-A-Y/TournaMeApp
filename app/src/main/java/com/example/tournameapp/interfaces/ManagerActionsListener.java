package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public interface ManagerActionsListener {

    Manager getManager();

    void onManagerTournamentsLoaded(List<Tournament> tournaments);

    void refresh();
}
