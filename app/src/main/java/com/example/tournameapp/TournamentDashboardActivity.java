package com.example.tournameapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.interfaces.TournamentListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.tournameapp.ui.main.SectionsPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class TournamentDashboardActivity extends AppCompatActivity implements TournamentDataListener {

    private TournamentDashboardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_tournament);
        Intent intent = getIntent();
        String tournamentID = intent.getExtras().getString("tournamentChose");
        String playerUsername = intent.getExtras().getString("player");
        presenter = new TournamentDashboardPresenter(this);
        presenter.loadData(tournamentID,playerUsername);
    }

    @Override
    public void onDataLoaded(Tournament tournament, List<Match> allMatches, List<Match> myMatches){
        Log.d("allMatches","size: "+allMatches.size()+","+allMatches.toString());
        Log.d("myMatches","size: "+myMatches.size()+","+myMatches.toString());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),tournament,allMatches,myMatches);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}