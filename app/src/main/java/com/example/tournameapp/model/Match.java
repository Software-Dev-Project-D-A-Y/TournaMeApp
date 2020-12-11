package com.example.tournameapp.model;

import androidx.annotation.Nullable;

public class Match {
    //matchID	tournamentID	homePlayerID	awayPlayerID	homeScore	awayScore	scoreIsUpdated

    private String id;
    private Tournament tournament;
    private Player homePlayer;
    private Player awayPlayer;
    private int homeScore;
    private int awayScore;
    private boolean isUpdated;

    public Match() {
    }

    public Match( Tournament tournament, Player homePlayer, Player awayPlayer) {
        this.tournament = tournament;
        this.homePlayer = homePlayer;
        this.awayPlayer = awayPlayer;
        this.homeScore = 0;
        this.awayScore = 0;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Player getHomePlayer() {
        return homePlayer;
    }

    public void setHomePlayer(Player homePlayer) {
        this.homePlayer = homePlayer;
    }

    public Player getAwayPlayer() {
        return awayPlayer;
    }

    public void setAwayPlayer(Player awayPlayer) {
        this.awayPlayer = awayPlayer;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    @Override
    public String toString() {
        return "{"+tournament.getId()+"}: "+homePlayer.getUserName()+" vs. "+awayPlayer.getUserName();
    }

    public String matchToString() {
        return  homePlayer.getUserName()+" vs. "+awayPlayer.getUserName();

    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Match)) return false;
        Match other = (Match) obj;
        return this.id.equals(other.id);
    }
}
