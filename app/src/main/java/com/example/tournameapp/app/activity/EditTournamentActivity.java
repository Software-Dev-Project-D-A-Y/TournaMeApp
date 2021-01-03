package com.example.tournameapp.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournameapp.R;
import com.example.tournameapp.app.fragment.ParticipantsFragment;
import com.example.tournameapp.app.fragment.TournamentFragment;
import com.example.tournameapp.app.fragment.TournamentMatchesFragment;
import com.example.tournameapp.interfaces.EditTournamentListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.presenters.EditTournamentPresenter;

import java.util.List;

public class EditTournamentActivity extends AppCompatActivity implements EditTournamentListener {

    private EditTournamentPresenter presenter;

    private Tournament tournament;

    private TextView playerAmountTxt;
    private Button inviteBtn;
    private Button startTournamentBtn;
    private Button viewTableBtn;
    private Button tournamentMatchesBtn;
    private Button participantsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_tournament);

        playerAmountTxt = (TextView) findViewById(R.id.playerAmountTxt);
        inviteBtn = (Button) findViewById(R.id.inviteBtn);
        startTournamentBtn = (Button) findViewById(R.id.startTournamentBtn);
        viewTableBtn = (Button) findViewById(R.id.viewTableBtn);
        tournamentMatchesBtn = (Button) findViewById(R.id.tournamentMatchesBtn);
        participantsBtn = (Button) findViewById(R.id.participantsBtn);

        Intent intent = getIntent();
        String tournamentID = intent.getExtras().getString("tournamentChose");

        presenter = new EditTournamentPresenter(this);
        presenter.loadTournament(tournamentID);

        inviteBtn.setEnabled(false);
        startTournamentBtn.setEnabled(false);
        viewTableBtn.setEnabled(false);
        tournamentMatchesBtn.setEnabled(false);
        participantsBtn.setEnabled(false);

        playerAmountTxt.setText("Loading data...");

    }

    private void inviteDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Invite a Player");
        alert.setMessage("Enter Username:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                presenter.sendInvite(value);
                return;
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alert.show();
    }

    @Override
    public void onTournamentLoaded(final Tournament tournament) {
        this.tournament = tournament;

        inviteBtn.setEnabled(true);
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteDialog();
            }
        });

        if(!tournament.isActive()) {
            startTournamentBtn.setEnabled(true);
        }

        viewTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TournamentFragment fragment = new TournamentFragment(tournament);
                fragment.show(getSupportFragmentManager(), "View Table");
            }
        });
    }

    @Override
    public void onTournamentPlayersLoaded(final List<Player> players) {
        participantsBtn.setEnabled(true);
        participantsBtn.setText("Participants ("+players.size()+")");

        playerAmountTxt.setText(players.size() + "/" + tournament.getCapacity() + " Players joined");

        if (players.size() < tournament.getCapacity()) {
            startTournamentBtn.setEnabled(false);
        }

        startTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startTournament(tournament);
            }
        });

        participantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParticipantsFragment fragment = new ParticipantsFragment(tournament,players);
                fragment.show(getSupportFragmentManager(), "Participants List");
            }
        });
    }

    @Override
    public void onTournamentAllMatchesLoaded(final List<Match> matches) {
        if(matches.size() == 0) {
            return;
        }
        viewTableBtn.setEnabled(true);
        tournamentMatchesBtn.setEnabled(true);
        tournamentMatchesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TournamentMatchesFragment fragment = new TournamentMatchesFragment(matches);
                fragment.show(getSupportFragmentManager(), "Update Score Table");
            }
        });
    }

    @Override
    public void onTournamentMatchesPlayedLoaded(final List<Match> matchesPlayed) {

    }

    @Override
    public void onTournamentStarted(String message) {
        refresh();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startTournamentBtn.setEnabled(false);
    }

    @Override
    public void onInviteUsernameError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvite(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInviteFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refresh() {
        presenter.loadTournament();
    }


}