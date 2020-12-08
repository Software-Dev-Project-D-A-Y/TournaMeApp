package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    TextView playerTextView;

    List<TournamentRequest> requests;
    UsersService usersService;
    RequestsService reqService;

    Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerTextView = (TextView) findViewById(R.id.playerTextView);

        Intent intent = getIntent();
        String playerLogged = intent.getExtras().getString("playerLogged");
        playerTextView.setText("Hello "+playerLogged);

        usersService = UsersService.getInstance();
        player = usersService.getPlayer(playerLogged);

        reqService = RequestsService.getInstance();

        requests = new ArrayList<>();

        reqService.loadPlayerRequests(player, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    TournamentRequest request = child.getValue(TournamentRequest.class);
                    requests.add(request);
                }
                Log.d("requests",requests.toString());
            }
        });

        /*
            NEED TO DELETE!
         */
        playerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("rememberMe",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rememberUser", false);
                editor.putString("loggedUser", "");
                editor.apply();

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
