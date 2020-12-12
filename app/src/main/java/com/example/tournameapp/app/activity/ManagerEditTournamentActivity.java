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
import com.example.tournameapp.app.fragment.TournamentFragment;
import com.example.tournameapp.app.fragment.UpdateScoreFragment;
import com.example.tournameapp.interfaces.TournamentEditListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.utils.ManagerEditTournamentPresenter;

import java.util.List;

public class ManagerEditTournamentActivity extends AppCompatActivity implements TournamentEditListener {

    private ManagerEditTournamentPresenter presenter;

    private Tournament tournament;

    private TextView playerAmountTxt;
    private Button inviteBtn;
    private Button startTournamentBtn;
    private Button viewTableBtn;
    private Button tournamentMatchesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_tournament);

        playerAmountTxt = (TextView) findViewById(R.id.playerAmountTxt);
        inviteBtn = (Button) findViewById(R.id.inviteBtn);
        startTournamentBtn = (Button) findViewById(R.id.startTournamentBtn);
        viewTableBtn = (Button) findViewById(R.id.viewTableBtn);
        tournamentMatchesBtn = (Button) findViewById(R.id.tournamentMatchesBtn);


        Intent intent = getIntent();
        String tournamentID = intent.getExtras().getString("tournamentChose");

        presenter = new ManagerEditTournamentPresenter(this);

        inviteBtn.setEnabled(false);
        playerAmountTxt.setText("Loading data...");

        Log.d("Tournament", "Before loading");

        presenter.loadTournament(tournamentID);

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
                        // TODO Auto-generated method stub
                        return;
                    }
                });
        alert.show();
    }

    @Override
    public void onTournamentLoaded(final Tournament tournament) {
        this.tournament = tournament;

        Log.d("Tournament", "after loading");
        Log.d("Tournament", tournament.toString());


        inviteBtn.setEnabled(true);
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteDialog();
            }
        });

        viewTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TournamentFragment fragment = new TournamentFragment(tournament);
                fragment.show(getSupportFragmentManager(), "View Table");
            }
        });
    }

    @Override
    public void onMatchUpdated(Match match) {
        presenter.updateMatch(match);
    }

    @Override
    public void onTournamentPlayersLoaded(List<Player> players) {
        Log.d("Tournament Players", players.toString());
        playerAmountTxt.setText(players.size() + "/" + tournament.getCapacity() + " Players joined");

        if (players.size() < tournament.getCapacity()) {
            return;
        }
        startTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startTournament(tournament);
            }
        });
    }

    @Override
    public void onTournamentAllMatchesLoaded(final List<Match> matches) {
        tournamentMatchesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateScoreFragment fragment = new UpdateScoreFragment(matches);
                fragment.show(getSupportFragmentManager(), "Update Score Table");

            }
        });
    }

    @Override
    public void onTournamentMatchesPlayedLoaded(final List<Match> matchesPlayed) {

    }

    @Override
    public void onTournamentStarted(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


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


}