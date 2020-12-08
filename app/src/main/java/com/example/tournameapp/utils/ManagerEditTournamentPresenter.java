package com.example.tournameapp.utils;

import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnInviteListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

public class ManagerEditTournamentPresenter {

    private OnInviteListener listener;

    private TournamentsService tourService;
    private UsersService usersService;
    private RequestsService reqService;

    private Tournament tournament;
    private Manager manager;

    public ManagerEditTournamentPresenter(OnInviteListener listener) {
        this.listener = listener;
        tourService = TournamentsService.getInstance();
        usersService = UsersService.getInstance();
        reqService = RequestsService.getInstance();
    }




    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void sendInvite(String username) {
        Player player = usersService.getPlayer(username);
        boolean isExists = player != null;
        if(!isExists) {
            listener.onUsernameError("Wrong Username!");
            return;
        }

        boolean isInserted = reqService.insertTournamentRequest(tournament,player);
        if (isInserted){
            listener.onInvite("Invite sent");
        }

    }
}
