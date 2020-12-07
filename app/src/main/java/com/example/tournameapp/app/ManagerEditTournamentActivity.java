package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tournameapp.R;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.DataSnapshot;

public class ManagerEditTournamentActivity extends AppCompatActivity {

    private TournamentsService service;
    private Tournament tournament;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_tournament);

        service = TournamentsService.getInstance();

        Intent intent = getIntent();
        String tournamentID = intent.getExtras().getString("tournamentID");

        service.getTournament(tournamentID, new OnDataLoadedListener() {
            @Override
            public void onStart() {
                Log.d("tournament","loading");
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                tournament = dataSnapshot.getValue(Tournament.class);
                Log.d("tournament",tournament.toString());
            }
        });
    }

}