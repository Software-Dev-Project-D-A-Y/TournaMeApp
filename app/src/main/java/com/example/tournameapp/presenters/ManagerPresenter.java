package com.example.tournameapp.presenters;

import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.ManagerActionsListener;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class ManagerPresenter {

    private TournamentsService tourService;
    private RequestsService reqService;
    private UsersService usersService;

    private Manager manager;

    private ManagerActionsListener observer;

    public ManagerPresenter(ManagerActionsListener observer, String managerLogged){
        this.usersService = UsersService.getInstance();
        this.tourService = TournamentsService.getInstance();
        this.reqService = RequestsService.getInstance();
        this.manager = usersService.getManager(managerLogged);
        this.observer = observer;
    }

    public void loadTournaments() {
        tourService.loadManagerTournaments(manager, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<Tournament> tournaments = new ArrayList<>();

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Tournament tournament = child.getValue(Tournament.class);
                    tournaments.add(tournament);
                }

                observer.onManagerTournamentsLoaded(tournaments);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    public Manager getManager() {
        return manager;
    }
}
