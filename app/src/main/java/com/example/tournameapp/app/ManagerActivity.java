package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.model.Manager;

public class ManagerActivity extends AppCompatActivity {

    private TextView managerTextView;
    private Button addNewTournamentBtn;
    private Button logoutButton;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        managerTextView = (TextView) findViewById(R.id.managerTextView);
        addNewTournamentBtn = (Button) findViewById(R.id.mAddTournamentBtn);
        logoutButton = (Button) findViewById(R.id.mLogoutBtn);

        Intent intent = getIntent();
        String managerLogged = intent.getExtras().getString("managerLogged");

        UsersService usersService = UsersService.getInstance();
        manager = usersService.getManager(managerLogged);
        Log.d("manager",manager.toString());

        managerTextView.setText("Hello "+managerLogged);



        addNewTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTournamentFragment addTournamentFragment = new AddTournamentFragment(manager);
                addTournamentFragment.show(getSupportFragmentManager(),"Add");
            }
        });



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
