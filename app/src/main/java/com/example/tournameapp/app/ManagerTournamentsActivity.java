package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManagerTournamentsActivity extends AppCompatActivity {

    private TextView loadingLbl;
    private ListView myTournamentsLv;


    private TournamentsService service;
    private Manager manager;
    private List<Tournament> tournaments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_tournaments);

        loadingLbl = (TextView) findViewById(R.id.loadingLbl);
        myTournamentsLv = (ListView) findViewById(R.id.myTournamentLv);

        Intent intent = getIntent();
        String managerUsername = intent.getExtras().getString("manager");

        UsersService usersService = UsersService.getInstance();
        manager = usersService.getManager(managerUsername);
        //

        tournaments = new ArrayList<>();

        service = TournamentsService.getInstance();
        service.loadManagerTournaments(manager, new OnDataLoadedListener() {
            @Override
            public void onStart() {
                loadingLbl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                loadingLbl.setVisibility(View.INVISIBLE);
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Tournament tournament = child.getValue(Tournament.class);
                    tournaments.add(tournament);
                }
                Log.d("tournaments",tournaments.toString());
                ArrayAdapter<Tournament> adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.layout_tournaments_list,tournaments);
                myTournamentsLv.setAdapter(adapter);

            }
        });

        myTournamentsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadingLbl.setVisibility(View.VISIBLE);
                loadingLbl.setText("Item clicked: "+position);
                Tournament tournamentChose = tournaments.get(position);
                loadingLbl.setText("Item clicked: "+tournamentChose);
            }
        });
    }


}
