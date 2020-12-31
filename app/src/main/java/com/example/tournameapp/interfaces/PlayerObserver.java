package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;

import java.util.List;

public interface PlayerObserver {
    
    Player getPlayer();

    void onMyRequestsSuccess(List<TournamentRequest> requests);
    void onRequestApproved(TournamentRequest requestChose);

    void onMyTournamentsSuccess(List<Tournament> tournaments);

    void onPlayerAddedToTournament(String message);
    void onAddFailure(String message);
    void onJoinRequestSuccess(String message);
    void onJoinRequestFailure(String message);


    void onPlayerLeaveClicked(Tournament tourToLeave);

    void onPlayerRemoved(String message);
}
