package com.example.tournameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView usernameTxt;
    private TextView passwordTxt;
    private CheckBox rememberMeCb;
    private Button signInBtn;
    private Button managerSignUpBtn;
    private Button playerSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTxt = (TextView) findViewById(R.id.usernameTxt);
        passwordTxt = (TextView) findViewById(R.id.passwordTxt);
        rememberMeCb = (CheckBox) findViewById(R.id.rememberMeCb);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        managerSignUpBtn = (Button) findViewById(R.id.signUpBtn);
        playerSignUpBtn = (Button) findViewById(R.id.playerSignUpBtn);

        managerSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                intent.putExtra("signedAs","Manager");
                startActivity(intent);
            }
        });

        playerSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                intent.putExtra("signedAs","Player");
                startActivity(intent);
            }
        });

    }
}
