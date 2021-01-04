package com.example.tournameapp.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournameapp.R;
import com.example.tournameapp.app.fragment.PlayerRequestsFragment;
import com.example.tournameapp.app.fragment.PlayerTournamentsFragment;
import com.example.tournameapp.interfaces.PlayerActionsListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;
import com.example.tournameapp.presenters.PlayerPresenter;

import java.util.List;

/**
 * Dashboard page of Player after login
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public class PlayerDashboardActivity extends AppCompatActivity implements PlayerActionsListener {

    // widgets
    private TextView playerTextView;
    private Button myRequestsBtn;
    private Button myTournamentsBtn;
    private Button joinTournamentBtn;
    private Button logoutBtn;

    // presenter
    private PlayerPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setTitle("Player Dashboard");

        // init widget
        playerTextView = (TextView) findViewById(R.id.playerTextView);
        myRequestsBtn = (Button) findViewById(R.id.pMyRequestsBtn);
        myTournamentsBtn = (Button) findViewById(R.id.pMyTournamentsBtn);
        joinTournamentBtn = (Button) findViewById(R.id.pJoinTournamentBtn);
        logoutBtn = (Button) findViewById(R.id.pLogoutBtn);

        // get logged user from intent
        Intent intent = getIntent();
        String playerLogged = intent.getExtras().getString("loggedUser");

        // init presenter and load data
        presenter = new PlayerPresenter(this, playerLogged);
        presenter.loadRequests();
        presenter.loadTournaments();

        playerTextView.setText("Hello " + playerLogged);

        // click events
        joinTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinTournamentDialog();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });

    }

    /**
     * Overrides onBackPressed to act like our custom logout
     */
    @Override
    public void onBackPressed() {
        logoutDialog();
    }

    // join dialog - enter a tournamentID
    private void joinTournamentDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Join a Tournament");
        alert.setMessage("Enter tournament ID");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String inputString = input.getText().toString();
               presenter.onJoinRequest(inputString);
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

    // logout dialog
    private void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Log Out");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                logout();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    // logout user using SharedPreferences
    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberUser", false);
        editor.putString("loggedUser", "");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public Player getPlayer() {
        return presenter.getPlayer();
    }

    @Override
    public void onPlayerRequestsLoaded(final List<TournamentRequest> requests) {
        myRequestsBtn.setText("My Requests ("+requests.size()+")");
        myRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerRequestsFragment fragment = new PlayerRequestsFragment(requests);
                fragment.show(getSupportFragmentManager(), "My Requests");
            }
        });
    }

    @Override
    public void onPlayerTournamentsLoaded(final List<Tournament> tournaments) {
        myTournamentsBtn.setText("My Tournaments ("+tournaments.size()+")");
        myTournamentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerTournamentsFragment fragment = new PlayerTournamentsFragment(tournaments);
                fragment.show(getSupportFragmentManager(),"My Tournaments");
            }
        });
    }

    @Override
    public void onJoinRequestSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onJoinRequestFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refresh() {
        presenter.loadRequests();
        presenter.loadTournaments();
    }
}
