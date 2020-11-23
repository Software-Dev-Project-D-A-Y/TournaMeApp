package com.example.tournameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String user = "aaaa";
        String pass = "123";

        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("Username",user);
        intent.putExtra("Password",pass);
        startActivity(intent);
    }
}
