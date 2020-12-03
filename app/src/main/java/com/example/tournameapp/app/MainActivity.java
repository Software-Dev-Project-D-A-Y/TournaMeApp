package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.database.UsersService;
import com.google.firebase.database.DataSnapshot;

public class MainActivity extends AppCompatActivity {

    private UsersService usersService;

    private TextView loadDataTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDataTxt = (TextView) findViewById(R.id.loadDataTxt);

        usersService = UsersService.getInstance();
        usersService.loadData(new UsersService.OnDataLoadedListener() {
            @Override
            public void onStart() {
                loadDataTxt.setText("Wait until data is loaded");
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

//        Intent intent = new Intent(this,LoginActivity.class);
//        startActivity(intent);



    }
}