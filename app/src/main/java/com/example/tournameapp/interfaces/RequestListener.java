package com.example.tournameapp.interfaces;

public interface RequestListener {

    void onRequestAccepted(String message);
    void onRequestDeclined(String message);

    void onRequestFailure(String message);
}
