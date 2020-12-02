package com.example.tournameapp.model;

public class Match {
    //matchID	tournamentID	homePlayerID	awayPlayerID	homeScore	awayScore	scoreIsUpdated

    private long id;
    private Tournament tournament;
    private Player homePlayer;
    private Player awayPlayer;
    private int homeScore;
    private int awayScore;
    private boolean isUpdated;

    public Match() {
    }

    public Match(long id, Tournament tournament, Player homePlayer, Player awayPlayer, int homeScore, int awayScore, boolean isUpdated) {
        this.id = id;
        this.tournament = tournament;
        this.homePlayer = homePlayer;
        this.awayPlayer = awayPlayer;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.isUpdated = isUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}