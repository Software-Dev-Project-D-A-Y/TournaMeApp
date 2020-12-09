package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public interface TournamentEditListener {

    void onTournamentLoad(Tournament tournament);
    void onInviteUsernameError(String message);
    void onInvite(String message);
    void onTournamentPlayersLoaded(List<Player> players);
}
