package com.example.tournameapp.interfaces;

import com.google.firebase.database.DataSnapshot;

public interface OnDataLoadedListener {
    void onStart();
    void onSuccess(DataSnapshot dataSnapshot);
}
