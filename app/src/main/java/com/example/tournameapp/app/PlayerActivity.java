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
import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.TournamentRequest;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private TextView playerTextView;
    private Button myRequestsBtn;
    private Button myTournamentsBtn;
    private Button joinTournamentBtn;
    private Button logoutBtn;

    private UsersService usersService;

    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        String playerLogged = intent.getExtras().getString("loggedUser");

        playerTextView = (TextView) findViewById(R.id.playerTextView);
        myRequestsBtn = (Button) findViewById(R.id.pMyRequestsBtn);
        myTournamentsBtn = (Button) findViewById(R.id.pMyTournamentsBtn);
        joinTournamentBtn = (Button) findViewById(R.id.pJoinTournamentBtn);
        logoutBtn = (Button) findViewById(R.id.pLogoutBtn);

        usersService = UsersService.getInstance();
        player = usersService.getPlayer(playerLogged);







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
}
