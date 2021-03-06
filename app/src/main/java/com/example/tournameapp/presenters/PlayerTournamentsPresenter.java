package com.example.tournameapp.presenters;

import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnLeaveListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

public class PlayerTournamentsPresenter {

    private TournamentsService tourService;

    private OnLeaveListener listener;
    private Player player;

    public PlayerTournamentsPresenter(OnLeaveListener listener, Player player) {
        this.tourService = TournamentsService.getInstance();

        this.listener = listener;
        this.player = player;
    }

    public void leaveTournament(Tournament tourToLeave) {
        tourService.removePlayerFromTournament(player,tourToLeave);
        listener.onLeave(player.getUserName()+" removed from "+tourToLeave.getTournamentName());
    }
}
