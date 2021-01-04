package com.example.tournameapp.app.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.TournamentDataListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.presenters.TournamentDashboardPresenter;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import com.example.tournameapp.adapters.TournamentSectionsPagerAdapter;

import java.util.List;

public class TournamentDashboardActivity extends AppCompatActivity implements TournamentDataListener {

    private TournamentDashboardPresenter presenter;
    private TextView tournamentNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_dashboard);

        tournamentNameTxt = (TextView) findViewById(R.id.tournamentNameTxt);

        Intent intent = getIntent();
        String tournamentID = intent.getExtras().getString("tournamentChose");
        String playerUsername = intent.getExtras().getString("player");

        presenter = new TournamentDashboardPresenter(this);
        presenter.loadData(tournamentID, playerUsername);
    }

    @Override
    public void onDataLoaded(Tournament tournament, List<Match> allMatches, List<Match> myMatches, Player playerToWatch) {
        tournamentNameTxt.setText(tournament.getTournamentName());

        TournamentSectionsPagerAdapter sectionsPagerAdapter = new TournamentSectionsPagerAdapter(this, getSupportFragmentManager(), tournament, allMatches, myMatches, playerToWatch);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}