package com.example.tournameapp.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Activity that loads all users data for future use in login and sign-up
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public class MainActivity extends AppCompatActivity {

    private UsersService usersService;
    private TextView loadDataTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDataTxt = findViewById(R.id.loadDataTxt);

        // instance of UsersService for loading users data
        usersService = UsersService.getInstance();
        usersService.loadUsersData(new OnDataLoadedListener() {
            @Override
            public void onStart() {
                loadDataTxt.setText("loading..");
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }
}