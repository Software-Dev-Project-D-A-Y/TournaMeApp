package com.example.tournameapp.interfaces;

import com.example.tournameapp.model.Tournament;

public interface OnLeaveListener {
    void onLeave(Tournament tourToLeave, int position);
}
