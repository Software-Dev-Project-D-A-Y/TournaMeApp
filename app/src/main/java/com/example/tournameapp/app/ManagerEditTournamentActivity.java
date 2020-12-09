package com.example.tournameapp.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tournameapp.R;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.interfaces.OnInviteListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.utils.ManagerEditTournamentPresenter;
import com.google.firebase.database.DataSnapshot;

public class ManagerEditTournamentActivity extends AppCompatActivity implements OnInviteListener {

    private TournamentsService service;
    private ManagerEditTournamentPresenter presenter;

    private Tournament tournament;
    private Manager manager;

    private Button inviteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_tournament);

        inviteBtn = (Button) findViewById(R.id.inviteBtn);

        service = TournamentsService.getInstance();
        presenter = new ManagerEditTournamentPresenter(this);


        Intent intent = getIntent();
        String tournamentID = intent.getExtras().getString("tournamentChose");

        service.loadTournament(tournamentID, new OnDataLoadedListener() {
            @Override
            public void onStart() {
                inviteBtn.setEnabled(false);
                Log.d("tournament","loading");
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                tournament = dataSnapshot.getValue(Tournament.class);
                manager = tournament.getManager();

                presenter.setTournament(tournament);
                presenter.setManager(manager);

                inviteBtn.setEnabled(true);
                inviteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inviteDialog();
                    }
                });
                Log.d("tournament",tournament.toString());
            }
        });
    }

    private void inviteDialog(){
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
    public void onUsernameError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvite(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}