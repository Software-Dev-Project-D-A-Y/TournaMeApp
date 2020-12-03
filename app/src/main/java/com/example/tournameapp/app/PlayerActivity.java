package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tournameapp.R;

public class PlayerActivity extends AppCompatActivity {

    TextView playerTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerTextView = (TextView) findViewById(R.id.playerTextView);

        Intent intent = getIntent();
        String playerLogged = intent.getExtras().getString("playerLogged");
        playerTextView.setText("Hello "+playerLogged);

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
