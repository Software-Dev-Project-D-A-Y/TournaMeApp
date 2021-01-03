package com.example.tournameapp.presenters;

import com.example.tournameapp.app.fragment.ParticipantsFragment;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.interfaces.OnKickListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

public class ParticipantsPresenter {

    private TournamentsService tourService;

    private OnKickListener listener;
    private Tournament tournament;

    public ParticipantsPresenter(OnKickListener listener, Tournament tournament) {
        this.tourService = TournamentsService.getInstance();

        this.listener = listener;
        this.tournament = tournament;
    }

    public void kickPlayer(Player player) {
        tourService.removePlayerFromTournament(player,tournament);
        listener.onKick(player.getUserName()+" has been removed");
    }
}
