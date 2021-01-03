package com.example.tournameapp.presenters;

import com.example.tournameapp.database.MatchesService;
import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnMatchUpdateListener;
import com.example.tournameapp.model.Match;

public class TournamentMatchesPresenter {

    private TournamentsService tourService;
    private UsersService usersService;
    private RequestsService reqService;
    private MatchesService matchesService;

    private OnMatchUpdateListener listener;

    public TournamentMatchesPresenter(OnMatchUpdateListener listener) {
        tourService = TournamentsService.getInstance();
        usersService = UsersService.getInstance();
        reqService = RequestsService.getInstance();
        matchesService = MatchesService.getInstance();
        this.listener = listener;
    }

    public void updateMatch(Match match) {
        matchesService.updateMatch(match);
        listener.onMatchUpdated("Match updated successfully");
    }
}
