package com.example.tournameapp.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.app.fragment.ManagerMyTournamentsFragment;
import com.example.tournameapp.app.fragment.AddTournamentFragment;
import com.example.tournameapp.interfaces.ManagerObserver;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.presenters.ManagerPresenter;

import java.util.List;

public class ManagerDashboardActivity extends AppCompatActivity implements ManagerObserver{

    private TextView managerTextView;
    private Button addNewTournamentBtn;
    private Button myTournamentsBtn;
    private Button logoutBtn;

    private ManagerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        setTitle("Manager Dashboard");


        managerTextView = (TextView) findViewById(R.id.managerTextView);
        addNewTournamentBtn = (Button) findViewById(R.id.mAddTournamentBtn);
        myTournamentsBtn = (Button) findViewById(R.id.mMyTournamentsBtn);
        logoutBtn = (Button) findViewById(R.id.mLogoutBtn);

        Intent intent = getIntent();
        String managerLogged = intent.getExtras().getString("loggedUser");

        presenter = new ManagerPresenter(this,managerLogged);

        managerTextView.setText("Hello "+managerLogged);

        addNewTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTournamentFragment fragment = new AddTournamentFragment(getManager());
                fragment.show(getSupportFragmentManager(),"Add");
            }
        });


        myTournamentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onMyTournamentClicked();
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        logoutDialog();
    }

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

    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("rememberMe",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberUser", false);
        editor.putString("loggedUser", "");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public Manager getManager() {
        return presenter.getManager();
    }

    @Override
    public void onAddTournamentSuccess(Manager manager) {

    }

    @Override
    public void onMyTournamentsSuccess(List<Tournament> tournaments) {
        ManagerMyTournamentsFragment fragment = new ManagerMyTournamentsFragment(tournaments);
        fragment.show(getSupportFragmentManager(),"My Tournaments");
    }
}
