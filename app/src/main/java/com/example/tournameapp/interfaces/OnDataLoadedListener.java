package com.example.tournameapp.interfaces;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface OnDataLoadedListener {
    void onStart();
    void onSuccess(DataSnapshot dataSnapshot);
    void onError(DatabaseError error);
}
