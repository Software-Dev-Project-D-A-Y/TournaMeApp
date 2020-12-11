package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public interface TournamentEditListener {

    void onTournamentLoaded(Tournament tournament);
    void onTournamentPlayersLoaded(List<Player> players);
    void onTournamentAllMatchesLoaded(List<Match> matches);
    void onTournamentMatchesPlayedLoaded(List<Match> matchesPlayed);
    void onTournamentStarted(String message);
    void onInviteUsernameError(String message);
    void onInviteFailure(String message);
    void onInvite(String message);


    void onMatchUpdated(Match match);
}
