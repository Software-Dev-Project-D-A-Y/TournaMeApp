package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tournameapp.R;

public class ManagerActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        textView = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        String managerLogged = intent.getExtras().getString("managerLogged");
        textView.setText("Hello "+managerLogged);
    }
}
