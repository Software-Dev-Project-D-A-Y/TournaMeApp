package com.example.tournameapp.model;

import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

public class TournamentRequest {
    //tournamentID	playerID	managerApprove	playerApprove
    private String id;

    private Tournament tournament;
    private Player player;
    private boolean managerApprove;
    private boolean playerApprove;

    public TournamentRequest() {
    }

    public TournamentRequest(Tournament tournament, Player player, boolean managerApprove, boolean playerApprove) {
        this.tournament = tournament;
        this.player = player;
        this.managerApprove = managerApprove;
        this.playerApprove = playerApprove;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isManagerApprove() {
        return managerApprove;
    }

    public void setManagerApprove(boolean managerApprove) {
        this.managerApprove = managerApprove;
    }

    public boolean isPlayerApprove() {
        return playerApprove;
    }

    public void setPlayerApprove(boolean playerApprove) {
        this.playerApprove = playerApprove;
    }

    @Override
    public String toString() {
        return tournament.getTournamentName()+" -> "+player.getUserName();
    }
}
