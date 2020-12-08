package com.example.tournameapp.utils;

import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.ManagerObserver;
import com.example.tournameapp.model.Manager;

public class ManagerPresenter {

    private TournamentsService tourService;
    private RequestsService reqService;
    private UsersService usersService;

    private Manager manager;

    private ManagerObserver observer;

    public ManagerPresenter(ManagerObserver observer, String managerLogged){
        usersService = UsersService.getInstance();
        tourService = TournamentsService.getInstance();
        reqService = RequestsService.getInstance();
        manager = usersService.getManager(managerLogged);
        this.observer = observer;
    }


}
