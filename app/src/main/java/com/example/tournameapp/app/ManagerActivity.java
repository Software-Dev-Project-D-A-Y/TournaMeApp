package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.ManagerObserver;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.utils.ManagerPresenter;

import java.util.List;

public class ManagerActivity extends AppCompatActivity implements ManagerObserver{

    private TextView managerTextView;
    private Button addNewTournamentBtn;
    private Button myTournamentsBtn;
    private Button logoutBtn;

    private ManagerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


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
                presenter.onAddTournamentClicked();
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
                logout();
            }
        });
    }

    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("rememberMe",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberUser", false);
        editor.putString("loggedUser", "");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAddTournamentSuccess(Manager manager) {
        AddTournamentFragment addTournamentFragment = new AddTournamentFragment(manager);
        addTournamentFragment.show(getSupportFragmentManager(),"Add");
    }

    @Override
    public void onMyTournamentsSuccess(List<Tournament> tournaments) {
        ManagerTournamentsFragment managerTournamentsFragment = new ManagerTournamentsFragment(tournaments);
        managerTournamentsFragment.show(getSupportFragmentManager(),"My Tournaments");
    }
}
