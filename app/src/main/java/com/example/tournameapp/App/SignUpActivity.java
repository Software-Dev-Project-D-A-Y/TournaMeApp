package com.example.tournameapp.App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tournameapp.R;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {

    private TextView signedAsTxt;
    private String signedAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        signedAs = intent.getExtras().getString("signedAs");

        signedAsTxt = (TextView) findViewById(R.id.signedAsTxt);
        signedAsTxt.setText("Signing as "+signedAs);
    }
}
