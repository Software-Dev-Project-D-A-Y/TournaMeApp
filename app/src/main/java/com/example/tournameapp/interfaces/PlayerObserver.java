package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;

import java.util.List;

public interface PlayerObserver {
    void onMyRequestsSuccess(List<TournamentRequest> requests);

    void onRequestApproved(TournamentRequest requestChose);

    void onPlayerAddedToTournament(String message);

    void onAddFailure(String message);

    void onJoinRequestSuccess(String message);
    void onJoinRequestFailure(String message);

    void onMyTournamentsSuccess(List<Tournament> tournaments);
}
