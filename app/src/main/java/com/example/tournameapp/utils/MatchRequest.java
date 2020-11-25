package com.example.tournameapp.utils;

import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;

public class MatchRequest {

    //matchID	tournamentID	isApproved
    private Match match;
    private Tournament tournament;
    private boolean isApproved;

    public MatchRequest() {
    }

    public MatchRequest(Match match, Tournament tournament, boolean isApproved) {
        this.match = match;
        this.tournament = tournament;
        this.isApproved = isApproved;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
