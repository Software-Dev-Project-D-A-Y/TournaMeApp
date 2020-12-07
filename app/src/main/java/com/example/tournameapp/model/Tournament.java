package com.example.tournameapp.model;

import com.example.tournameapp.model.Manager;

import java.util.Date;

public class Tournament {
    //tournamentID	managerID	isActive	tournamentDate	tournamentName	tournamentDescription	capacity	isJoinable
    private long id;
    private Manager manager;
    private boolean isActive;
    private Date tournamentDate;
    private String tournamentName;
    private String tournamentDescription;
    private int capacity;
    private boolean isJoinable;

    public Tournament() {
    }
    public Tournament(Manager manager, String tournamentName, String tournamentDescription, int capacity) {
        this.manager = manager;
        this.tournamentName = tournamentName;
        this.tournamentDescription = tournamentDescription;
        this.capacity = capacity;
    }

    public Tournament(Manager manager, boolean isActive, Date tournamentDate, String tournamentName, String tournamentDescription, int capacity, boolean isJoinable) {
        this.manager = manager;
        this.isActive = isActive;
        this.tournamentDate = tournamentDate;
        this.tournamentName = tournamentName;
        this.tournamentDescription = tournamentDescription;
        this.capacity = capacity;
        this.isJoinable = isJoinable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getTournamentDate() {
        return tournamentDate;
    }

    public void setTournamentDate(Date tournamentDate) {
        this.tournamentDate = tournamentDate;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentDescription() {
        return tournamentDescription;
    }

    public void setTournamentDescription(String tournamentDescription) {
        this.tournamentDescription = tournamentDescription;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isJoinable() {
        return isJoinable;
    }

    public void setJoinable(boolean joinable) {
        isJoinable = joinable;
    }
}
