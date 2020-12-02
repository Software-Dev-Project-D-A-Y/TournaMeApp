package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.LoginListener;
import com.example.tournameapp.utils.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private TextView usernameTxt;
    private TextView passwordTxt;
    private CheckBox rememberMeCb;
    private Button signInBtn;
    private Button managerSignUpBtn;
    private Button playerSignUpBtn;
    private LoginPresenter loginPresenter;
//checking
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);
        usernameTxt = (TextView) findViewById(R.id.usernameTxt);
        passwordTxt = (TextView) findViewById(R.id.passwordTxt);
        rememberMeCb = (CheckBox) findViewById(R.id.rememberMeCb);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        managerSignUpBtn = (Button) findViewById(R.id.signUpBtn);
        playerSignUpBtn = (Button) findViewById(R.id.playerSignUpBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                boolean rememberMe = rememberMeCb.isChecked();
                loginPresenter.logIn(userName, password, rememberMe);

            }
        });

        managerSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                intent.putExtra("signAs","Manager");
                startActivity(intent);
            }
        });

        playerSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                intent.putExtra("signAs","Player");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onUserNameError(String message) {

    }

    @Override
    public void onPasswordError(String message) {

    }
}
