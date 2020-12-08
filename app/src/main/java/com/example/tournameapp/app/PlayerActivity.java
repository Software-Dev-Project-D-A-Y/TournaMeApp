package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.PlayerObserver;
import com.example.tournameapp.model.TournamentRequest;
import com.example.tournameapp.utils.PlayerPresenter;

import java.util.List;

public class PlayerActivity extends AppCompatActivity implements PlayerObserver {

    private TextView playerTextView;
    private Button myRequestsBtn;
    private Button myTournamentsBtn;
    private Button joinTournamentBtn;
    private Button logoutBtn;

    private PlayerPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        String playerLogged = intent.getExtras().getString("loggedUser");

        presenter = new PlayerPresenter(this,playerLogged);

        playerTextView = (TextView) findViewById(R.id.playerTextView);
        myRequestsBtn = (Button) findViewById(R.id.pMyRequestsBtn);
        myTournamentsBtn = (Button) findViewById(R.id.pMyTournamentsBtn);
        joinTournamentBtn = (Button) findViewById(R.id.pJoinTournamentBtn);
        logoutBtn = (Button) findViewById(R.id.pLogoutBtn);




        myRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onMyRequestsClicked();
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
    public void onMyRequestsSuccess(List<TournamentRequest> requests) {
        PlayerRequestsFragment fragment = new PlayerRequestsFragment(requests);
        fragment.show(getSupportFragmentManager(),"My Requests");
    }

    @Override
    public void onRequestApproved(TournamentRequest requestChose) {
        presenter.setRequestApproved(requestChose);
    }

    @Override
    public void onPlayerAddedToTournament(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
