package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Tournament;

public interface OnLeaveListener {
    void onLeaveTournamentClicked(Tournament tourToLeave);
    void onLeave(String message);
}
