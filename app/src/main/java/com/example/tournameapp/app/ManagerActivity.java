package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tournameapp.R;

public class ManagerActivity extends AppCompatActivity {

    TextView managerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        managerTextView = (TextView) findViewById(R.id.managerTextView);

        Intent intent = getIntent();
        String managerLogged = intent.getExtras().getString("managerLogged");
        managerTextView.setText("Hello "+managerLogged);

        /*
            NEED TO DELETE!
         */
        managerTextView.setOnClickListener(new View.OnClickListener() {
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
