package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;

import java.util.List;

/**
 * Interface listening to Player actions
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public interface PlayerActionsListener {

    /**
     * @return player logged
     */
    Player getPlayer();

    /**
     * called when player's requests are loaded
     * @param requests list of requests loaded
     */
    void onPlayerRequestsLoaded(List<TournamentRequest> requests);

    /**
     * called when player's tournaments are loaded
     * @param tournaments list of tournaments loaded
     */
    void onPlayerTournamentsLoaded(List<Tournament> tournaments);

    /**
     * called when join succeed
     * @param message message to show
     */
    void onJoinRequestSuccess(String message);

    /**
     * called when failure occurred on join
     * @param message message to show
     */
    void onJoinRequestFailure(String message);

    /**
     * reload all tournaments and requests
     */
    void refresh();
}
