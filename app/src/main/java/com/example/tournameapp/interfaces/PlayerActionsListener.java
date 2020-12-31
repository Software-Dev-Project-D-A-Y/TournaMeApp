package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;

import java.util.List;

public interface PlayerActionsListener {
    
    Player getPlayer();

    void onPlayerRequestsLoaded(List<TournamentRequest> requests);
    void onPlayerTournamentsLoaded(List<Tournament> tournaments);

    void onJoinRequestSuccess(String message);
    void onJoinRequestFailure(String message);

    void onPlayerLeaveClicked(Tournament tourToLeave);
    void onPlayerRemoved(String message);

    void refresh();
}
