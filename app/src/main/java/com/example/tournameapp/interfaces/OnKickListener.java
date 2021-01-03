package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Player;

public interface OnKickListener {
    void onKickPlayerClicked(Player player);
    void onKick(String message);
}
