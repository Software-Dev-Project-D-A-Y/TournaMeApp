package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.utils.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpListener {

    private TextView signAsTxt;
    private String signAs;

    private TextView firstNameSignUpTxt;
    private TextView lastNameSignUpTxt;
    private TextView usernameSignUpTxt;
    private TextView ageSignUpTxt;
    private TextView passwordSignUpTxt;
    private TextView confirmPassSignUpTxt;
    private TextView emailSignUpTxt;
    private Button signUpBtn;

    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpPresenter = new SignUpPresenter(this);

        Intent intent = getIntent();
        signAs = intent.getExtras().getString("signAs");

        firstNameSignUpTxt = (TextView) findViewById(R.id.firstNameSignUpTxt);
        lastNameSignUpTxt = (TextView) findViewById(R.id.lastNameSignUpTxt);
        usernameSignUpTxt = (TextView) findViewById(R.id.usernameSignUpTxt);
        passwordSignUpTxt= (TextView) findViewById(R.id.passwordSignUpTxt);
        confirmPassSignUpTxt = (TextView) findViewById(R.id.confirmPassSignUpTxt);
        ageSignUpTxt = (TextView) findViewById(R.id.ageSignUpTxt);
        emailSignUpTxt = (TextView) findViewById(R.id.emailSignUpTxt);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        signAsTxt = (TextView) findViewById(R.id.signAsTxt);
        signAsTxt.setText("Signing as "+signAs);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameSignUpTxt.getText().toString();
                String lastName = lastNameSignUpTxt.getText().toString();
                String username = usernameSignUpTxt.getText().toString();
                String password = passwordSignUpTxt.getText().toString();
                String confirmPassword = confirmPassSignUpTxt.getText().toString();
                String email = emailSignUpTxt.getText().toString();

                int age = -1;
                try {
                    age = Integer.parseInt( ageSignUpTxt.getText().toString());
                } catch (Exception e1) {
                    age = -1;
                }

                switch (signAs) {
                    case "Manager":
                        signUpPresenter.signUpManager(firstName,lastName,age,email,username,password,confirmPassword);
                        break;

                    case "Player":
                        signUpPresenter.signUpPlayer(firstName,lastName,age,email,username,password,confirmPassword);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onFirstNameError(String message) {
        firstNameSignUpTxt.setError(message);
    }

    @Override
    public void onLastNameError(String message) {
        lastNameSignUpTxt.setError(message);
    }

    @Override
    public void onAgeError(String message) {
        ageSignUpTxt.setError(message);
    }

    @Override
    public void onUsernameError(String message) {
        usernameSignUpTxt.setError(message);
    }

    @Override
    public void onPasswordError(String message) {
        passwordSignUpTxt.setError(message);
    }

    @Override
    public void onMatchedPasswordsError(String message) {
        confirmPassSignUpTxt.setError(message);
    }

    @Override
    public void onEmailError(String message) {
        emailSignUpTxt.setError(message);
    }

    @Override
    public void signUp() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
